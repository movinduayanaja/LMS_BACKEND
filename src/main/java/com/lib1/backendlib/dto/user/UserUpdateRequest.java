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
public class UserUpdateRequest {

    @NotBlank @Email @Size(max = 255)
    private String email;

    @NotBlank @Size(max = 255)
    private String password;

    @NotNull
    private Role role;

    @NotNull
    private Boolean blacklisted;
}