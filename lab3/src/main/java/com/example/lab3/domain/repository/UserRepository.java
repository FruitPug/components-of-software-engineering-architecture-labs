package com.example.lab3.domain.repository;

import com.example.lab3.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);

    User save(User user);
}
