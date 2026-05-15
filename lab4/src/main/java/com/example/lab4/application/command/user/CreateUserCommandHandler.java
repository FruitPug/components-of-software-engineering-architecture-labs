package com.example.lab4.application.command.user;

import com.example.lab4.domain.factory.UserFactory;
import com.example.lab4.domain.model.User;
import com.example.lab4.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserCommandHandler {

    private final UserRepository repository;
    private final UserFactory factory;

    @Transactional
    public void handle(CreateUserCommand cmd) {
        User user = factory.create(
                cmd.email(),
                cmd.fullName(),
                cmd.password(),
                cmd.role()
        );

        User savedUser = repository.save(user);
        user.setId(savedUser.getId());
    }
}
