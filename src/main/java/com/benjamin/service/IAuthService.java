package com.benjamin.service;

import com.benjamin.request.SignupRequest;

public interface IAuthService {
    String createUser(SignupRequest request);
}
