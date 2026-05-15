package com.example.lab4.domain.model;

import com.example.lab4.domain.enums.ProjectStatus;
import com.example.lab4.domain.error.DomainError;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class Project {

    @Setter
    private Long id;
    private final String name;
    private final String description;
    private ProjectStatus status;

    private boolean deleted;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public Project(String name, String description) {
        if (name == null || name.isBlank()) {
            throw new DomainError("PROJECT_NAME_REQUIRED");
        }

        this.name = name;
        this.description = description;
        this.status = ProjectStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.deleted = false;
    }

    public Project(Long id, String name, String description, ProjectStatus status,
                   boolean deleted, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public void updateStatus(ProjectStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public void softDelete() {
        if (deleted) return;

        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
