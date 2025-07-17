package com.benjamin.service.impl;

import com.benjamin.config.JwtProvider;
import com.benjamin.domain.EUserRole;
import com.benjamin.model.CartModel;
import com.benjamin.model.SellerModel;
import com.benjamin.model.UserModel;
import com.benjamin.model.VerificationCode;
import com.benjamin.repository.ICartRepository;
import com.benjamin.repository.ISellerRepository;
import com.benjamin.repository.IUserRepository;
import com.benjamin.repository.IVerificationCodeRepository;
import com.benjamin.request.LoginRequest;
import com.benjamin.request.SignupRequest;
import com.benjamin.response.AuthResponse;
import com.benjamin.service.IAuthService;
import com.benjamin.service.IEmailService;
import com.benjamin.util.OtpUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.benjamin.domain.EUserRole.ROLE_CUSTOMER;
import static com.benjamin.domain.EUserRole.ROLE_SELLER;
import static com.benjamin.util.Constant.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService implements IAuthService {

    IVerificationCodeRepository verificationCodeRepository;
    ICartRepository cartRepository;
    IEmailService emailService;
    IUserRepository userRepository;
    ISellerRepository sellerRepository;
    PasswordEncoder passwordEncoder;
    JwtProvider jwtProvider;
    CustomUserService customUserService;

    @Override
    public String createUser(SignupRequest request) throws Exception {

        UserModel user = userRepository.findByEmail(request.getEmail());
        VerificationCode verificationCode = verificationCodeRepository.findByEmail(request.getEmail());

        if (verificationCode == null || !verificationCode.getOtp().equals(request.getOtp())) {
            throw new Exception("wrong otp");
        }

        if (user == null) {
            UserModel newUser = UserModel.builder()
                    .email(request.getEmail())
                    .fullName(request.getFullName())
                    .role(ROLE_CUSTOMER)
                    .phoneNumber("0374769666")
                    .password(passwordEncoder.encode(request.getOtp()))
                    .build();

            userRepository.save(newUser);

            CartModel cart = CartModel.builder()
                    .user(newUser)
                    .build();
            cartRepository.save(cart);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(ROLE_CUSTOMER.toString()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                null,
                authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.generateToken(authentication);
    }

    @Override
    public void sentLoginOtp(String email, EUserRole role) {
        if (email.startsWith(SIGNING_PREFIX)){
            email = email.substring(SIGNING_PREFIX.length());

            if (role.equals(ROLE_SELLER)) {
                SellerModel seller = sellerRepository.findByEmail(email);
                if (seller == null) {
                    throw new RuntimeException("Seller not found with email: " + email);
                }
            } else {
                UserModel user = userRepository.findByEmail(email);
                if (user == null) {
                    throw new RuntimeException("User not found with email: " + email);
                }
            }
        }

        VerificationCode code = verificationCodeRepository.findByEmail(email);
        if (code != null) {
            verificationCodeRepository.delete(code);
        }

        String otp = OtpUtil.generateOtp();
        VerificationCode verificationCode = VerificationCode.builder()
                .otp(otp)
                .email(email)
                .build();
        verificationCodeRepository.save(verificationCode);
        String text = MAIL_OTP_TEXT + otp;

        emailService.sendVerificationEmail(email, otp, MAIL_OTP_SUBJECT, text);
    }

    @Override
    public AuthResponse signing(LoginRequest request) {
        String username = request.getEmail();
        String otp = request.getOtp();

        Authentication authentication = authenticate(username, otp);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtProvider.generateToken(authentication);
        AuthResponse response = AuthResponse.builder()
                .token(jwtToken)
                .message("Login Successful")
                .build();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roleName = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        response.setRole(EUserRole.valueOf(roleName));

        return response;
    }

    private Authentication authenticate(String username, String otp) {
        UserDetails user = customUserService.loadUserByUsername(username);
        if (user == null) {
            throw new BadCredentialsException("Invalid username");
        }

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(username);
        if (verificationCode == null || !verificationCode.getOtp().equals(otp)) {
            throw new BadCredentialsException("Wrong OTP");
        }
        return new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
    }
}
