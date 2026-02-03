package com.lib1.backendlib.service;

import com.lib1.backendlib.dto.user.*;

import java.util.List;

public interface UserService {
    UserResponse create(UserCreateRequest req);
    UserResponse getById(Integer id);
    List<UserResponse> getAll();
    UserResponse update(Integer id, UserUpdateRequest req);
    void delete(Integer id);
}