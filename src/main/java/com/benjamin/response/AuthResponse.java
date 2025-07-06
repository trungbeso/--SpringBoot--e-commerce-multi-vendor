package com.benjamin.response;

import com.benjamin.domain.EUserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String token;
    private String message;
    private EUserRole role;
}
