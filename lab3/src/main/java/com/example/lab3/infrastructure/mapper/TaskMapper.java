package com.example.lab3.infrastructure.mapper;

import com.example.lab3.domain.model.Task;
import com.example.lab3.infrastructure.persistence.entity.ProjectEntity;
import com.example.lab3.infrastructure.persistence.entity.TaskEntity;
import com.example.lab3.infrastructure.persistence.entity.UserEntity;

public class TaskMapper {

    public static Task toDomain(TaskEntity e) {
        return new Task(
                e.getId(),
                e.getProject().getId(),
                e.getCreator().getId(),
                e.getAssignee() != null ? e.getAssignee().getId() : null,
                e.getTitle(),
                e.getDescription(),
                e.getStatus(),
                e.getPriority(),
                e.getDueDate(),
                e.isDeleted(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getDeletedAt(),
                e.getVersion()
        );
    }

    public static TaskEntity toEntity(
            Task t,
            ProjectEntity project,
            UserEntity creator,
            UserEntity assignee
    ) {
        return TaskEntity.builder()
                .id(t.getId())
                .project(project)
                .creator(creator)
                .assignee(assignee)
                .title(t.getTitle())
                .description(t.getDescription())
                .status(t.getStatus())
                .priority(t.getPriority())
                .dueDate(t.getDueDate())
                .createdAt(t.getCreatedAt())
                .updatedAt(t.getUpdatedAt())
                .deleted(t.isDeleted())
                .deletedAt(t.getDeletedAt())
                .version(t.getVersion())
                .build();
    }
}
