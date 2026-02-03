package com.lib1.backendlib.dto.user;

import com.lib1.backendlib.domain.enums.Role;
import lombok.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Integer id;
    private String email;
    private Role role;
    private boolean blacklisted;
    private Instant createdAt;
}

