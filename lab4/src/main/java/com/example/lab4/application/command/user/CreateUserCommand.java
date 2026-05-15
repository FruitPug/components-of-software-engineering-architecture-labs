package com.example.lab4.application.command.user;

import com.example.lab4.domain.enums.UserRole;

public record CreateUserCommand(
        String email,
        String fullName,
        String password,
        UserRole role
) {
}
