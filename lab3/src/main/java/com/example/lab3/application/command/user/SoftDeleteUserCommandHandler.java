package com.example.lab3.application.command.user;

import com.example.lab3.domain.error.DomainError;
import com.example.lab3.domain.model.User;
import com.example.lab3.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SoftDeleteUserCommandHandler {

    private final UserRepository repository;

    @Transactional
    public void handle(SoftDeleteUserCommand cmd) {
        User user = repository.findById(cmd.id())
                .orElseThrow(() -> new DomainError("USER_NOT_FOUND"));

        user.softDelete();
        repository.save(user);
    }
}
