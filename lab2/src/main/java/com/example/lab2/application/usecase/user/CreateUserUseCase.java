package com.example.lab2.application.usecase.user;

import com.example.lab2.application.command.user.CreateUserCommand;
import com.example.lab2.domain.factory.UserFactory;
import com.example.lab2.domain.model.User;
import com.example.lab2.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserRepository repository;
    private final UserFactory factory;

    @Transactional
    public void execute(CreateUserCommand cmd) {
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
