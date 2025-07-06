package com.benjamin.controller;

import com.benjamin.model.UserModel;
import com.benjamin.response.ApiResponse;
import com.benjamin.response.AuthResponse;
import com.benjamin.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/v1/users")
public class UserController {

    IUserService userService;

    @PostMapping("profile")
    public ResponseEntity<UserModel> createUserHandler(@RequestHeader("Authorization") String jwt) {
        UserModel user = userService.findByJwtToken(jwt);
        return ResponseEntity.ok(user);
    }
}
