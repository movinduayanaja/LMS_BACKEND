package com.lib1.backendlib.service.impl;

import com.lib1.backendlib.domain.entity.User;
import com.lib1.backendlib.domain.repository.UserRepository;
import com.lib1.backendlib.dto.auth.AuthResponse;
import com.lib1.backendlib.dto.auth.LoginRequest;
import com.lib1.backendlib.dto.auth.SignupRequest;
import com.lib1.backendlib.dto.user.UserResponse;
import com.lib1.backendlib.mapper.UserReservationMapper;
import com.lib1.backendlib.security.JwtService;
import com.lib1.backendlib.service.AuthService;
import com.lib1.backendlib.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final long expiresInMs;
    private final MailService mailService;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService,
                           @Value("${app.jwt.expiration-ms}") long expiresInMs,
                           MailService mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.expiresInMs = expiresInMs;
        this.mailService = mailService;
    }

    @Override
    public UserResponse signup(SignupRequest req) {
        if (userRepository.existsByEmailIgnoreCase(req.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User u = new User();
        u.setEmail(req.getEmail().trim());
        u.setPassword(passwordEncoder.encode(req.getPassword())); // BCrypt
        u.setRole(req.getRole());
        u.setBlacklisted(false);

        User saved = userRepository.save(u);

        // Send confirmation email (non-critical; MailService should handle its own exceptions)
        mailService.sendSignupConfirmation(saved.getEmail());

        return UserReservationMapper.toUserResponse(saved);
    }

    @Override
    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByEmailIgnoreCase(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (user.isBlacklisted()) {
            throw new IllegalStateException("This account is blacklisted");
        }

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        Map<String, Object> claims = Map.of(
                "role", user.getRole().name(),
                "userId", user.getId()
        );

        String token = jwtService.generateToken(user.getEmail(), claims);

        return new AuthResponse(token, user.getId(), user.getEmail(), user.getRole(), expiresInMs);
    }
}