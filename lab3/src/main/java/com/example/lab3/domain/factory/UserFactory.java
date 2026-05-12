package com.example.lab3.domain.factory;

import com.example.lab3.domain.error.DomainError;
import com.example.lab3.domain.model.User;
import com.example.lab3.domain.repository.UserRepository;
import com.example.lab3.domain.service.PasswordHasher;
import com.example.lab3.domain.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFactory {

    private final UserRepository repository;
    private final PasswordHasher passwordHasher;

    public User create(String email, String fullName, String rawPassword, UserRole role) {

        repository.findByEmail(email).ifPresent(u -> {
            throw new DomainError("EMAIL_ALREADY_EXISTS");
        });

        String hashedPassword = passwordHasher.hash(rawPassword);

        return new User(null, email, fullName, hashedPassword, role);
    }
}
