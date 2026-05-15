package com.example.lab4.unit.domain.factory;

import com.example.lab4.domain.enums.UserRole;
import com.example.lab4.domain.error.DomainError;
import com.example.lab4.domain.factory.UserFactory;
import com.example.lab4.domain.model.User;
import com.example.lab4.domain.repository.UserRepository;
import com.example.lab4.domain.service.PasswordHasher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserFactoryTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordHasher passwordHasher;

    @InjectMocks
    private UserFactory userFactory;

    @Test
    void shouldCreateUserSuccessfully() {
        String email = "test@example.com";
        String fullName = "Full Name";
        String rawPassword = "password";
        UserRole role = UserRole.DEVELOPER;
        String hashedPassword = "hashed_password";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(passwordHasher.hash(rawPassword)).thenReturn(hashedPassword);

        User user = userFactory.create(email, fullName, rawPassword, role);

        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(fullName, user.getFullName());
        assertEquals(hashedPassword, user.getPasswordHash());
        assertEquals(role, user.getRole());
        assertNull(user.getId());

        verify(userRepository).findByEmail(email);
        verify(passwordHasher).hash(rawPassword);
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        String email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mock(User.class)));

        DomainError exception = assertThrows(DomainError.class, () -> 
            userFactory.create(email, "Name", "pass", UserRole.DEVELOPER)
        );

        assertEquals("EMAIL_ALREADY_EXISTS", exception.getMessage());
        verify(userRepository).findByEmail(email);
        verify(passwordHasher, never()).hash(anyString());
    }
}
