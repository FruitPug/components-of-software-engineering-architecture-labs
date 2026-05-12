package com.example.lab3.unit.application.usecase.user;

import com.example.lab3.application.command.user.CreateUserCommand;
import com.example.lab3.application.usecase.user.CreateUserUseCase;
import com.example.lab3.domain.enums.UserRole;
import com.example.lab3.domain.factory.UserFactory;
import com.example.lab3.domain.model.User;
import com.example.lab3.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserFactory factory;

    @InjectMocks
    private CreateUserUseCase useCase;

    @Test
    void execute_ShouldCreateAndSaveUser() {
        CreateUserCommand cmd = new CreateUserCommand("test@example.com", "John Doe", "password", UserRole.DEVELOPER);
        User user = mock(User.class);
        User savedUser = mock(User.class);
        when(savedUser.getId()).thenReturn(1L);
        when(factory.create(cmd.email(), cmd.fullName(), cmd.password(), cmd.role())).thenReturn(user);
        when(repository.save(user)).thenReturn(savedUser);

        useCase.execute(cmd);

        verify(factory).create(cmd.email(), cmd.fullName(), cmd.password(), cmd.role());
        verify(repository).save(user);
        verify(user).setId(1L);
    }
}
