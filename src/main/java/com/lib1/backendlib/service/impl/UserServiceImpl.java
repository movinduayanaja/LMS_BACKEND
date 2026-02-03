package com.lib1.backendlib.service.impl;

import com.lib1.backendlib.domain.entity.User;
import com.lib1.backendlib.domain.repository.UserRepository;
import com.lib1.backendlib.dto.user.*;
import com.lib1.backendlib.exception.NotFoundException;
import com.lib1.backendlib.mapper.UserReservationMapper;
import com.lib1.backendlib.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserResponse create(UserCreateRequest req) {
        if (repo.existsByEmailIgnoreCase(req.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        User u = new User();
        u.setEmail(req.getEmail().trim());
        u.setPassword(req.getPassword()); // hash later with BCrypt (Day 8–10)
        u.setRole(req.getRole());
        u.setBlacklisted(req.getBlacklisted() != null ? req.getBlacklisted() : false);
        return UserReservationMapper.toUserResponse(repo.save(u));
    }

    @Override @Transactional(readOnly = true)
    public UserResponse getById(Integer id) {
        User u = repo.findById(id).orElseThrow(() ->
            new NotFoundException("User not found: " + id));
        return UserReservationMapper.toUserResponse(u);
    }

    @Override @Transactional(readOnly = true)
    public java.util.List<UserResponse> getAll() {
        return repo.findAll().stream().map(UserReservationMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse update(Integer id, UserUpdateRequest req) {
        User u = repo.findById(id).orElseThrow(() ->
            new NotFoundException("User not found: " + id));

        if (!u.getEmail().equalsIgnoreCase(req.getEmail())
                && repo.existsByEmailIgnoreCase(req.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        u.setEmail(req.getEmail().trim());
        u.setPassword(req.getPassword()); // hash later with BCrypt
        u.setRole(req.getRole());
        u.setBlacklisted(req.getBlacklisted());

        return UserReservationMapper.toUserResponse(repo.save(u));
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("User not found: " + id);
        }
        repo.deleteById(id);
    }
}
