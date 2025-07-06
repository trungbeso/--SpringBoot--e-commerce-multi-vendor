package com.benjamin.service;

import com.benjamin.model.UserModel;

public interface IUserService {
    UserModel findByEmail(String email);
    UserModel findByJwtToken(String jwt);
}
