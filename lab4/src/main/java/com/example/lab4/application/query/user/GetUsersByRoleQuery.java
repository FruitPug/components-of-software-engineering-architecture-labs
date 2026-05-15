package com.example.lab4.application.query.user;

import com.example.lab4.domain.enums.UserRole;
import org.springframework.data.domain.Pageable;

public record GetUsersByRoleQuery(
        UserRole role,
        Pageable pageable
) {
}
