package com.benjamin.controller;

import com.benjamin.model.UserModel;
import com.benjamin.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/v1/users")
public class UserController {

    IUserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserModel> createUserHandler(@RequestHeader("Authorization") String jwt) {
        UserModel user = userService.findByJwtToken(jwt);
        return ResponseEntity.ok(user);
    }
}
