package com.lib1.backendlib.service;

import com.lib1.backendlib.dto.auth.LoginRequest;
import com.lib1.backendlib.dto.auth.SignupRequest;
import com.lib1.backendlib.dto.auth.AuthResponse;
import com.lib1.backendlib.dto.user.UserResponse;

public interface AuthService {

    UserResponse signup(SignupRequest req);

    AuthResponse login(LoginRequest req);
}