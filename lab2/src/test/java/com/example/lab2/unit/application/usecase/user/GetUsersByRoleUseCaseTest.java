package com.example.lab2.unit.application.usecase.user;

import com.example.lab2.application.usecase.user.GetUsersByRoleUseCase;
import com.example.lab2.domain.enums.UserRole;
import com.example.lab2.domain.model.User;
import com.example.lab2.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUsersByRoleUseCaseTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private GetUsersByRoleUseCase useCase;

    @Test
    @SuppressWarnings("unchecked")
    void execute_ShouldReturnUsersByRole() {
        UserRole role = UserRole.DEVELOPER;
        Pageable pageable = mock(Pageable.class);
        Page<User> expectedPage = mock(Page.class);
        when(repository.findByRole(role, pageable)).thenReturn(expectedPage);

        Page<User> result = useCase.execute(role, pageable);

        assertEquals(expectedPage, result);
        verify(repository).findByRole(role, pageable);
    }
}
