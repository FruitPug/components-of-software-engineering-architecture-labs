package com.example.lab3.domain.model;

import com.example.lab3.domain.error.DomainError;
import com.example.lab3.domain.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class User {

    @Setter
    private Long id;

    private final String email;
    private final String fullName;
    private final String passwordHash;
    private UserRole role;

    @Setter
    private boolean deleted;
    @Setter
    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime updatedAt;
    @Setter
    private LocalDateTime deletedAt;

    public User(Long id, String email, String fullName, String passwordHash, UserRole role) {
        if (email == null || !email.contains("@")) {
            throw new DomainError("INVALID_EMAIL");
        }

        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.passwordHash = passwordHash;
        this.role = role;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.deleted = false;
    }

    public void changeRole(UserRole role) {
        this.role = role;
        this.updatedAt = LocalDateTime.now();
    }

    public void softDelete() {
        if (this.deleted) return;

        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}