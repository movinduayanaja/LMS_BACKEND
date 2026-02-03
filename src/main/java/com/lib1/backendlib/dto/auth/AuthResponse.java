package com.lib1.backendlib.dto.auth;

import com.lib1.backendlib.domain.enums.Role;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class AuthResponse {
    private String token;
    private Integer userId;
    private String email;
    private Role role;
    private long expiresInMs;
}