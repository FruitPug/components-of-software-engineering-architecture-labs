package com.example.lab3.application.query.user;

import com.example.lab3.domain.enums.UserRole;
import lombok.Builder;

@Builder
public record UserReadModel(
        Long id,
        String email,
        String fullName,
        UserRole role
) {
}
