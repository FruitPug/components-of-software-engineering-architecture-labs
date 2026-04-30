package com.example.lab2.unit.domain.model;

import com.example.lab2.domain.enums.TaskPriority;
import com.example.lab2.domain.enums.TaskStatus;
import com.example.lab2.domain.error.DomainError;
import com.example.lab2.domain.model.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void shouldCreateTaskSuccessfully() {
        LocalDate dueDate = LocalDate.now().plusDays(7);
        Task task = new Task(1L, 2L, 3L, "Title", "Desc", TaskPriority.HIGH, dueDate);

        assertEquals(1L, task.getProjectId());
        assertEquals(2L, task.getCreatorId());
        assertEquals(3L, task.getAssigneeId());
        assertEquals("Title", task.getTitle());
        assertEquals("Desc", task.getDescription());
        assertEquals(TaskPriority.HIGH, task.getPriority());
        assertEquals(dueDate, task.getDueDate());
        assertEquals(TaskStatus.TODO, task.getStatus());
        assertFalse(task.isDeleted());
        assertNotNull(task.getCreatedAt());
        assertNotNull(task.getUpdatedAt());
    }

    @Test
    void shouldThrowExceptionWhenTitleIsNull() {
        DomainError exception = assertThrows(DomainError.class, () -> 
            new Task(1L, 2L, 3L, null, "Desc", TaskPriority.MEDIUM, LocalDate.now())
        );
        assertEquals("TASK_TITLE_REQUIRED", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTitleIsBlank() {
        DomainError exception = assertThrows(DomainError.class, () -> 
            new Task(1L, 2L, 3L, "  ", "Desc", TaskPriority.MEDIUM, LocalDate.now())
        );
        assertEquals("TASK_TITLE_REQUIRED", exception.getMessage());
    }

    @Test
    void shouldUpdateStatus() {
        Task task = new Task(1L, 2L, 3L, "Title", "Desc", TaskPriority.LOW, LocalDate.now());
        LocalDateTime beforeUpdate = task.getUpdatedAt();

        task.updateStatus(TaskStatus.IN_PROGRESS);

        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
        assertTrue(task.getUpdatedAt().isAfter(beforeUpdate) || task.getUpdatedAt().equals(beforeUpdate));
    }

    @Test
    void shouldThrowExceptionOnInvalidStatusTransition() {
        Task task = new Task(1L, 2L, 3L, "Title", "Desc", TaskPriority.LOW, LocalDate.now());
        task.updateStatus(TaskStatus.DONE);

        DomainError exception = assertThrows(DomainError.class, () -> task.updateStatus(TaskStatus.TODO));
        assertEquals("INVALID_STATUS_TRANSITION", exception.getMessage());
    }

    @Test
    void shouldReassign() {
        Task task = new Task(1L, 2L, 3L, "Title", "Desc", TaskPriority.LOW, LocalDate.now());
        LocalDateTime beforeUpdate = task.getUpdatedAt();

        task.reassign(4L);

        assertEquals(4L, task.getAssigneeId());
        assertTrue(task.getUpdatedAt().isAfter(beforeUpdate) || task.getUpdatedAt().equals(beforeUpdate));
    }

    @Test
    void shouldSoftDelete() {
        Task task = new Task(1L, 2L, 3L, "Title", "Desc", TaskPriority.LOW, LocalDate.now());
        assertFalse(task.isDeleted());

        task.softDelete();

        assertTrue(task.isDeleted());
        assertNotNull(task.getDeletedAt());
        assertNotNull(task.getUpdatedAt());
    }

    @Test
    void shouldNotUpdateDeletedAtIfAlreadyDeleted() {
        Task task = new Task(1L, 2L, 3L, "Title", "Desc", TaskPriority.LOW, LocalDate.now());
        task.softDelete();
        LocalDateTime firstDeletedAt = task.getDeletedAt();

        task.softDelete();

        assertEquals(firstDeletedAt, task.getDeletedAt());
    }

    @Test
    void shouldCreateWithAllFields() {
        LocalDateTime now = LocalDateTime.now();
        LocalDate due = LocalDate.now();
        Task task = new Task(1L, 2L, 3L, 4L, "T", "D", TaskStatus.IN_PROGRESS, TaskPriority.LOW, due, false, now, now, null, 1L);

        assertEquals(1L, task.getId());
        assertEquals(2L, task.getProjectId());
        assertEquals(3L, task.getCreatorId());
        assertEquals(4L, task.getAssigneeId());
        assertEquals("T", task.getTitle());
        assertEquals("D", task.getDescription());
        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
        assertEquals(TaskPriority.LOW, task.getPriority());
        assertEquals(due, task.getDueDate());
        assertFalse(task.isDeleted());
        assertEquals(now, task.getCreatedAt());
        assertEquals(now, task.getUpdatedAt());
        assertNull(task.getDeletedAt());
        assertEquals(1L, task.getVersion());
    }
}
