package com.example.lab3.unit.application.usecase.user;

import com.example.lab3.application.command.user.SoftDeleteUserCommand;
import com.example.lab3.application.command.user.SoftDeleteUserCommandHandler;
import com.example.lab3.domain.error.DomainError;
import com.example.lab3.domain.model.User;
import com.example.lab3.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SoftDeleteUserCommandHandlerTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private SoftDeleteUserCommandHandler handler;

    @Test
    void handle_WhenUserExists_ShouldSoftDeleteAndSave() {
        Long userId = 1L;
        SoftDeleteUserCommand cmd = new SoftDeleteUserCommand(userId);
        User user = mock(User.class);
        when(repository.findById(userId)).thenReturn(Optional.of(user));

        handler.handle(cmd);

        verify(user).softDelete();
        verify(repository).save(user);
    }

    @Test
    void handle_WhenUserDoesNotExist_ShouldThrowException() {
        Long userId = 1L;
        SoftDeleteUserCommand cmd = new SoftDeleteUserCommand(userId);
        when(repository.findById(userId)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> handler.handle(cmd));
        assertEquals("USER_NOT_FOUND", exception.getMessage());
        verify(repository, never()).save(any());
    }
}
