package com.example.lab2.application.usecase.user;

import com.example.lab2.domain.enums.UserRole;

public record CreateUserCommand(
        String email,
        String fullName,
        String password,
        UserRole role
) {
}
