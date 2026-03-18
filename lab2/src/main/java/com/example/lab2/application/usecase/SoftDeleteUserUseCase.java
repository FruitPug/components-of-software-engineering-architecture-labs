package com.example.lab2.application.usecase;

import com.example.lab2.domain.error.DomainError;
import com.example.lab2.domain.model.User;
import com.example.lab2.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SoftDeleteUserUseCase {

    private final UserRepository repository;

    public void execute(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new DomainError("USER_NOT_FOUND"));

        user.softDelete();
        repository.save(user);
    }
}
