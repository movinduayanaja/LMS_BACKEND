package com.lib1.backendlib.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class LoginRequest {
    @NotBlank @Email @Size(max = 255)
    private String email;

    @NotBlank @Size(max = 255)
    private String password;
}