package com.lib1.backendlib.dto.user;

import com.lib1.backendlib.domain.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {

    @NotBlank @Email @Size(max = 255)
    private String email;

    @NotBlank @Size(max = 255)
    private String password; // plain for now; will hash on Day 8–10

    @NotNull
    private Role role;

    // optional; default false
    private Boolean blacklisted;
}
