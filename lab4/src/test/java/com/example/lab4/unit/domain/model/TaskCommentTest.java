package com.example.lab4.unit.domain.model;

import com.example.lab4.domain.error.DomainError;
import com.example.lab4.domain.model.TaskComment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskCommentTest {

    @Test
    void shouldCreateTaskCommentSuccessfully() {
        TaskComment comment = new TaskComment(1L, 2L, "Great job!");

        assertEquals(1L, comment.getTaskId());
        assertEquals(2L, comment.getAuthorId());
        assertEquals("Great job!", comment.getBody());
        assertFalse(comment.isDeleted());
        assertNotNull(comment.getCreatedAt());
        assertNotNull(comment.getUpdatedAt());
    }

    @Test
    void shouldThrowExceptionWhenBodyIsNull() {
        DomainError exception = assertThrows(DomainError.class, () -> new TaskComment(1L, 2L, null));
        assertEquals("COMMENT_BODY_REQUIRED", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenBodyIsBlank() {
        DomainError exception = assertThrows(DomainError.class, () -> new TaskComment(1L, 2L, "   "));
        assertEquals("COMMENT_BODY_REQUIRED", exception.getMessage());
    }

    @Test
    void shouldSoftDelete() {
        TaskComment comment = new TaskComment(1L, 2L, "Body");
        assertFalse(comment.isDeleted());

        comment.softDelete();

        assertTrue(comment.isDeleted());
        assertNotNull(comment.getDeletedAt());
        assertNotNull(comment.getUpdatedAt());
    }

    @Test
    void shouldNotUpdateDeletedAtIfAlreadyDeleted() {
        TaskComment comment = new TaskComment(1L, 2L, "Body");
        comment.softDelete();
        LocalDateTime firstDeletedAt = comment.getDeletedAt();

        comment.softDelete();

        assertEquals(firstDeletedAt, comment.getDeletedAt());
    }

    @Test
    void shouldCreateWithAllFields() {
        LocalDateTime now = LocalDateTime.now();
        TaskComment comment = new TaskComment(10L, 1L, 2L, "B", false, now, now, null);

        assertEquals(10L, comment.getId());
        assertEquals(1L, comment.getTaskId());
        assertEquals(2L, comment.getAuthorId());
        assertEquals("B", comment.getBody());
        assertFalse(comment.isDeleted());
        assertEquals(now, comment.getCreatedAt());
        assertEquals(now, comment.getUpdatedAt());
        assertNull(comment.getDeletedAt());
    }
}
