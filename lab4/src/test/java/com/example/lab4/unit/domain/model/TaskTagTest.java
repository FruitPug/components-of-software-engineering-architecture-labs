package com.example.lab4.unit.domain.model;

import com.example.lab4.domain.error.DomainError;
import com.example.lab4.domain.model.TaskTag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTagTest {

    @Test
    void shouldCreateTaskTagSuccessfully() {
        TaskTag taskTag = new TaskTag(1L, 2L);

        assertEquals(1L, taskTag.getTaskId());
        assertEquals(2L, taskTag.getTagId());
    }

    @Test
    void shouldThrowExceptionWhenTaskIdIsNull() {
        DomainError exception = assertThrows(DomainError.class, () -> new TaskTag(null, 2L));
        assertEquals("INVALID_TASK_TAG_REFERENCE", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTagIdIsNull() {
        DomainError exception = assertThrows(DomainError.class, () -> new TaskTag(1L, null));
        assertEquals("INVALID_TASK_TAG_REFERENCE", exception.getMessage());
    }

    @Test
    void shouldCreateWithAllFields() {
        TaskTag taskTag = new TaskTag(10L, 1L, 2L);

        assertEquals(10L, taskTag.getId());
        assertEquals(1L, taskTag.getTaskId());
        assertEquals(2L, taskTag.getTagId());
    }
}
