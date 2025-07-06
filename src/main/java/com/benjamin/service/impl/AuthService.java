package com.benjamin.service.impl;

import com.benjamin.config.JwtProvider;
import com.benjamin.model.CartModel;
import com.benjamin.model.UserModel;
import com.benjamin.repository.ICartRepository;
import com.benjamin.repository.IUserRepository;
import com.benjamin.request.SignupRequest;
import com.benjamin.service.IAuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.benjamin.domain.EUserRole.ROLE_CUSTOMER;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService implements IAuthService {

    ICartRepository cartRepository;
    IUserRepository userRepository;
    PasswordEncoder passwordEncoder;
    JwtProvider jwtProvider;

    @Override
    public String createUser(SignupRequest request) {
        UserModel user = userRepository.findByEmail(request.getEmail());
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
}
