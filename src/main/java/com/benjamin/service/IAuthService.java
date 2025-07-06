package com.benjamin.service;

import com.benjamin.request.LoginRequest;
import com.benjamin.request.SignupRequest;
import com.benjamin.response.AuthResponse;

public interface IAuthService {
    String createUser(SignupRequest request) throws Exception;
    void sentLoginOtp(String email);
    AuthResponse login(LoginRequest request);
}
