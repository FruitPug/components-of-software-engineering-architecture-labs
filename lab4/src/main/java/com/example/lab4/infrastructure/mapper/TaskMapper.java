package com.example.lab4.infrastructure.mapper;

import com.example.lab4.application.query.task.TaskReadModel;
import com.example.lab4.domain.model.Task;
import com.example.lab4.infrastructure.persistence.entity.ProjectEntity;
import com.example.lab4.infrastructure.persistence.entity.TaskEntity;
import com.example.lab4.infrastructure.persistence.entity.UserEntity;

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

    public static TaskReadModel toReadModel(TaskEntity e) {
        return TaskReadModel.builder()
                .id(e.getId())
                .projectId(e.getProject().getId())
                .creatorId(e.getCreator().getId())
                .assigneeId(e.getAssignee().getId())
                .title(e.getTitle())
                .description(e.getDescription())
                .status(e.getStatus())
                .priority(e.getPriority())
                .dueDate(e.getDueDate())
                .build();
    }
}
