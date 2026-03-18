package com.example.lab2.application.usecase;

import com.example.lab2.sorting_bin.entity.enums.UserRole;

public record CreateUserCommand(
        String email,
        String fullName,
        String password,
        UserRole role
) {
}
