package com.example.lab3.application.query.user;

import com.example.lab3.domain.enums.UserRole;
import org.springframework.data.domain.Pageable;

public record GetUsersByRoleQuery(
        UserRole role,
        Pageable pageable
) {
}
