package com.example.lab3.domain.repository;

import com.example.lab3.domain.model.User;
import com.example.lab3.domain.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Page<User> findByRole(UserRole role, Pageable pageable);

    User save(User user);
}
