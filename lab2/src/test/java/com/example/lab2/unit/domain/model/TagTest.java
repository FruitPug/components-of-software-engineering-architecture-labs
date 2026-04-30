package com.example.lab2.unit.domain.model;

import com.example.lab2.domain.error.DomainError;
import com.example.lab2.domain.model.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {

    @Test
    void shouldCreateTagSuccessfully() {
        Tag tag = new Tag("Urgent", "#FF0000");

        assertEquals("Urgent", tag.getName());
        assertEquals("#FF0000", tag.getColor());
        assertFalse(tag.isDeleted());
        assertNotNull(tag.getCreatedAt());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        DomainError exception = assertThrows(DomainError.class, () -> new Tag(null, "#FF0000"));
        assertEquals("TAG_NAME_REQUIRED", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        DomainError exception = assertThrows(DomainError.class, () -> new Tag("  ", "#FF0000"));
        assertEquals("TAG_NAME_REQUIRED", exception.getMessage());
    }

    @Test
    void shouldSoftDelete() {
        Tag tag = new Tag("Urgent", "#FF0000");
        assertFalse(tag.isDeleted());

        tag.softDelete();

        assertTrue(tag.isDeleted());
        assertNotNull(tag.getDeletedAt());
    }

    @Test
    void shouldNotUpdateDeletedAtIfAlreadyDeleted() {
        Tag tag = new Tag("Urgent", "#FF0000");
        tag.softDelete();
        LocalDateTime firstDeletedAt = tag.getDeletedAt();

        tag.softDelete();

        assertEquals(firstDeletedAt, tag.getDeletedAt());
    }

    @Test
    void shouldCreateWithAllFields() {
        LocalDateTime now = LocalDateTime.now();
        Tag tag = new Tag(1L, "Bug", "Blue", false, now, null);

        assertEquals(1L, tag.getId());
        assertEquals("Bug", tag.getName());
        assertEquals("Blue", tag.getColor());
        assertFalse(tag.isDeleted());
        assertEquals(now, tag.getCreatedAt());
        assertNull(tag.getDeletedAt());
    }
}
