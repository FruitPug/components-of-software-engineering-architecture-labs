package com.example.lab3.application.usecase.user;

import com.example.lab3.domain.error.DomainError;
import com.example.lab3.domain.model.User;
import com.example.lab3.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SoftDeleteUserUseCase {

    private final UserRepository repository;

    @Transactional
    public void execute(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new DomainError("USER_NOT_FOUND"));

        user.softDelete();
        repository.save(user);
    }
}
