package com.example.lab3.application.query.user;

import com.example.lab3.domain.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserReadRepository {

    Page<UserReadModel> findByRole(
            UserRole role,
            Pageable pageable
    );
}
