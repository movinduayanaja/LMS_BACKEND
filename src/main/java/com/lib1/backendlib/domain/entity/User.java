package com.lib1.backendlib.domain.entity;

import com.lib1.backendlib.domain.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(name = "is_blacklisted", nullable = false)
    private boolean blacklisted = false;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Instant createdAt;
}