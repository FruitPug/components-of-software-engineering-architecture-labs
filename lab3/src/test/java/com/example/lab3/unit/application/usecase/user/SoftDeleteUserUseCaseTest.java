package com.example.lab3.unit.application.usecase.user;

import com.example.lab3.application.usecase.user.SoftDeleteUserUseCase;
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
class SoftDeleteUserUseCaseTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private SoftDeleteUserUseCase useCase;

    @Test
    void execute_WhenUserExists_ShouldSoftDeleteAndSave() {
        Long userId = 1L;
        User user = mock(User.class);
        when(repository.findById(userId)).thenReturn(Optional.of(user));

        useCase.execute(userId);

        verify(user).softDelete();
        verify(repository).save(user);
    }

    @Test
    void execute_WhenUserDoesNotExist_ShouldThrowException() {
        Long userId = 1L;
        when(repository.findById(userId)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.execute(userId));
        assertEquals("USER_NOT_FOUND", exception.getMessage());
        verify(repository, never()).save(any());
    }
}
