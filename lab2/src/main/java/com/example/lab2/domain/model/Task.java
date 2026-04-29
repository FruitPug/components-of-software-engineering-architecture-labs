package com.example.lab2.domain.model;

import com.example.lab2.domain.enums.TaskPriority;
import com.example.lab2.domain.enums.TaskStatus;
import com.example.lab2.domain.error.DomainError;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class Task {

    @Setter
    private Long id;

    private final Long projectId;
    private final Long creatorId;
    private Long assigneeId;

    private final String title;
    private final String description;

    private TaskStatus status;
    private final TaskPriority priority;

    private final LocalDate dueDate;

    private boolean deleted;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    private Long version;

    public Task(Long projectId,
                Long creatorId,
                Long assigneeId,
                String title,
                String description,
                TaskPriority priority,
                LocalDate dueDate) {

        if (title == null || title.isBlank()) {
            throw new DomainError("TASK_TITLE_REQUIRED");
        }

        this.projectId = projectId;
        this.creatorId = creatorId;
        this.assigneeId = assigneeId;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;

        this.status = TaskStatus.TODO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.deleted = false;
    }

    public Task(Long id, Long projectId, Long creatorId, Long assigneeId,
                String title, String description,
                TaskStatus status, TaskPriority priority,
                LocalDate dueDate,
                boolean deleted,
                LocalDateTime createdAt,
                LocalDateTime updatedAt,
                LocalDateTime deletedAt,
                Long version) {

        this.id = id;
        this.projectId = projectId;
        this.creatorId = creatorId;
        this.assigneeId = assigneeId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.version = version;
    }

    public void updateStatus(TaskStatus newStatus) {
        if (this.status == TaskStatus.DONE && newStatus == TaskStatus.TODO) {
            throw new DomainError("INVALID_STATUS_TRANSITION");
        }

        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }

    public void reassign(Long newAssigneeId) {
        this.assigneeId = newAssigneeId;
        this.updatedAt = LocalDateTime.now();
    }

    public void softDelete() {
        if (deleted) return;

        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
