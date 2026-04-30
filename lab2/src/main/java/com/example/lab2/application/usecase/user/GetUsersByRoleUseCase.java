package com.example.lab2.application.usecase.user;

import com.example.lab2.domain.model.User;
import com.example.lab2.domain.repository.UserRepository;
import com.example.lab2.domain.enums.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUsersByRoleUseCase {

    private final UserRepository repository;

    @Transactional
    public Page<User> execute(UserRole role, Pageable pageable) {
        return repository.findByRole(role, pageable);
    }
}
