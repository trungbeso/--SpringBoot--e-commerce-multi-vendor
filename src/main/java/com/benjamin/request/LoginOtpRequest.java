package com.benjamin.request;

import com.benjamin.domain.EUserRole;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginOtpRequest {
    String email;
    String otp;
    EUserRole role;
}
