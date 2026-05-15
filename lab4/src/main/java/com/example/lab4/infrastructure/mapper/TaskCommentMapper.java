package com.example.lab4.infrastructure.mapper;

import com.example.lab4.application.query.task_comment.TaskCommentReadModel;
import com.example.lab4.domain.model.TaskComment;
import com.example.lab4.infrastructure.persistence.entity.TaskEntity;
import com.example.lab4.infrastructure.persistence.entity.UserEntity;
import com.example.lab4.infrastructure.persistence.entity.TaskCommentEntity;

public class TaskCommentMapper {

    public static TaskComment toDomain(TaskCommentEntity e) {
        return new TaskComment(
                e.getId(),
                e.getTask().getId(),
                e.getAuthor().getId(),
                e.getBody(),
                e.isDeleted(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getDeletedAt()
        );
    }

    public static TaskCommentEntity toEntity(
            TaskComment c,
            TaskEntity task,
            UserEntity author
    ) {
        return TaskCommentEntity.builder()
                .id(c.getId())
                .task(task)
                .author(author)
                .body(c.getBody())
                .createdAt(c.getCreatedAt())
                .updatedAt(c.getUpdatedAt())
                .deleted(c.isDeleted())
                .deletedAt(c.getDeletedAt())
                .build();
    }

    public static TaskCommentReadModel toReadModel(TaskCommentEntity e) {
        return TaskCommentReadModel.builder()
                .id(e.getId())
                .taskId(e.getTask().getId())
                .authorId(e.getAuthor().getId())
                .body(e.getBody())
                .build();
    }
}
