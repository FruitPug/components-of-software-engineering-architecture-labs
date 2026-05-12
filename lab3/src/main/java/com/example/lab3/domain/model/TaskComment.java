package com.example.lab3.domain.model;

import com.example.lab3.domain.error.DomainError;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class TaskComment {

    @Setter
    private Long id;
    private final Long taskId;
    private final Long authorId;
    private final String body;

    private boolean deleted;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public TaskComment(Long taskId, Long authorId, String body) {
        if (body == null || body.isBlank()) {
            throw new DomainError("COMMENT_BODY_REQUIRED");
        }

        this.taskId = taskId;
        this.authorId = authorId;
        this.body = body;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.deleted = false;
    }

    public TaskComment(Long id, Long taskId, Long authorId, String body,
                       boolean deleted, LocalDateTime createdAt,
                       LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.taskId = taskId;
        this.authorId = authorId;
        this.body = body;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public void softDelete() {
        if (deleted) return;

        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
