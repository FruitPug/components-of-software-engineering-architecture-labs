package com.example.lab4.domain.repository;

import com.example.lab4.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);

    User save(User user);
}
