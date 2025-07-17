package com.benjamin.controller;

import com.benjamin.repository.IUserRepository;
import com.benjamin.request.LoginOtpRequest;
import com.benjamin.request.LoginRequest;
import com.benjamin.request.SignupRequest;
import com.benjamin.response.ApiResponse;
import com.benjamin.response.AuthResponse;
import com.benjamin.service.IAuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.benjamin.domain.EUserRole.ROLE_CUSTOMER;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/v1/auth")
public class AuthController {

    IUserRepository userRepository;
    IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest request) throws Exception {

        String jwt = authService.createUser(request);

        AuthResponse response = AuthResponse.builder()
                .token(jwt)
                .message("User registered successfully")
                .role(ROLE_CUSTOMER)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/sent/login-signup-otp")
    public ResponseEntity<ApiResponse> sendOtpHandler(@RequestBody LoginOtpRequest request) {
        authService.sentLoginOtp(request.getEmail(), request.getRole()  );

        ApiResponse response = new ApiResponse();
        response.setMessage("OTP sent successfully");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest request) {
        AuthResponse response = authService.signing(request);

        return ResponseEntity.ok(response);
    }
}
