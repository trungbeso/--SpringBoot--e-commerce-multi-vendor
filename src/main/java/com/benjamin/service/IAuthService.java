package com.benjamin.service;

import com.benjamin.domain.EUserRole;
import com.benjamin.request.LoginRequest;
import com.benjamin.request.SignupRequest;
import com.benjamin.response.AuthResponse;

public interface IAuthService {
    String createUser(SignupRequest request) throws Exception;
    void sentLoginOtp(String email, EUserRole role);
    AuthResponse signing(LoginRequest request);
}
