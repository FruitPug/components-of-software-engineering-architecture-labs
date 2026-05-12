package com.example.lab3.unit.domain.model;

import com.example.lab3.domain.enums.UserRole;
import com.example.lab3.domain.error.DomainError;
import com.example.lab3.domain.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldCreateUserSuccessfully() {
        User user = new User(1L, "test@example.com", "Test User", "hash", UserRole.DEVELOPER);

        assertEquals(1L, user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("Test User", user.getFullName());
        assertEquals("hash", user.getPasswordHash());
        assertEquals(UserRole.DEVELOPER, user.getRole());
        assertFalse(user.isDeleted());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsInvalid() {
        DomainError exception = assertThrows(DomainError.class, () -> 
            new User(1L, "invalid-email", "Test User", "hash", UserRole.DEVELOPER)
        );
        assertEquals("INVALID_EMAIL", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
        DomainError exception = assertThrows(DomainError.class, () -> 
            new User(1L, null, "Test User", "hash", UserRole.DEVELOPER)
        );
        assertEquals("INVALID_EMAIL", exception.getMessage());
    }

    @Test
    void shouldChangeRole() {
        User user = new User(1L, "test@example.com", "Test User", "hash", UserRole.DEVELOPER);
        LocalDateTime beforeUpdate = user.getUpdatedAt();
        
        user.changeRole(UserRole.ADMIN);
        
        assertEquals(UserRole.ADMIN, user.getRole());
        assertTrue(user.getUpdatedAt().isAfter(beforeUpdate) || user.getUpdatedAt().equals(beforeUpdate));
    }

    @Test
    void shouldSoftDelete() {
        User user = new User(1L, "test@example.com", "Test User", "hash", UserRole.DEVELOPER);
        assertFalse(user.isDeleted());
        assertNull(user.getDeletedAt());

        user.softDelete();

        assertTrue(user.isDeleted());
        assertNotNull(user.getDeletedAt());
        assertNotNull(user.getUpdatedAt());
    }

    @Test
    void shouldNotUpdateDeletedAtIfAlreadyDeleted() {
        User user = new User(1L, "test@example.com", "Test User", "hash", UserRole.DEVELOPER);
        user.softDelete();
        LocalDateTime firstDeletedAt = user.getDeletedAt();

        user.softDelete();

        assertEquals(firstDeletedAt, user.getDeletedAt());
    }
}
