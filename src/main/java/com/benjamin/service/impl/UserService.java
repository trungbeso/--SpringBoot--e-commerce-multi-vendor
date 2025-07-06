package com.benjamin.service.impl;

import com.benjamin.config.JwtProvider;
import com.benjamin.model.UserModel;
import com.benjamin.repository.IUserRepository;
import com.benjamin.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IUserService {

    IUserRepository userRepository;
    JwtProvider jwtProvider;

    @Override
    public UserModel findByEmail(String email) {
        UserModel user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found with email: " + email);
        }
        return user;
    }

    @Override
    public UserModel findByJwtToken(String jwt) {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return this.findByEmail(email);
    }
}
