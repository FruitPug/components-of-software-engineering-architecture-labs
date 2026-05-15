package com.example.lab4.unit.domain.model;

import com.example.lab4.domain.enums.ProjectStatus;
import com.example.lab4.domain.error.DomainError;
import com.example.lab4.domain.model.Project;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    @Test
    void shouldCreateProjectSuccessfully() {
        Project project = new Project("Test Project", "Description");

        assertEquals("Test Project", project.getName());
        assertEquals("Description", project.getDescription());
        assertEquals(ProjectStatus.ACTIVE, project.getStatus());
        assertFalse(project.isDeleted());
        assertNotNull(project.getCreatedAt());
        assertNotNull(project.getUpdatedAt());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        DomainError exception = assertThrows(DomainError.class, () -> new Project(null, "Description"));
        assertEquals("PROJECT_NAME_REQUIRED", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        DomainError exception = assertThrows(DomainError.class, () -> new Project("   ", "Description"));
        assertEquals("PROJECT_NAME_REQUIRED", exception.getMessage());
    }

    @Test
    void shouldUpdateStatus() {
        Project project = new Project("Test Project", "Description");
        LocalDateTime beforeUpdate = project.getUpdatedAt();

        project.updateStatus(ProjectStatus.ARCHIVED);

        assertEquals(ProjectStatus.ARCHIVED, project.getStatus());
        assertTrue(project.getUpdatedAt().isAfter(beforeUpdate) || project.getUpdatedAt().equals(beforeUpdate));
    }

    @Test
    void shouldSoftDelete() {
        Project project = new Project("Test Project", "Description");
        assertFalse(project.isDeleted());

        project.softDelete();

        assertTrue(project.isDeleted());
        assertNotNull(project.getDeletedAt());
        assertNotNull(project.getUpdatedAt());
    }

    @Test
    void shouldNotUpdateDeletedAtIfAlreadyDeleted() {
        Project project = new Project("Test Project", "Description");
        project.softDelete();
        LocalDateTime firstDeletedAt = project.getDeletedAt();

        project.softDelete();

        assertEquals(firstDeletedAt, project.getDeletedAt());
    }

    @Test
    void shouldCreateProjectWithAllFields() {
        LocalDateTime now = LocalDateTime.now();
        Project project = new Project(1L, "Name", "Desc", ProjectStatus.ACTIVE, false, now, now, null);

        assertEquals(1L, project.getId());
        assertEquals("Name", project.getName());
        assertEquals("Desc", project.getDescription());
        assertEquals(ProjectStatus.ACTIVE, project.getStatus());
        assertFalse(project.isDeleted());
        assertEquals(now, project.getCreatedAt());
        assertEquals(now, project.getUpdatedAt());
        assertNull(project.getDeletedAt());
    }
}
