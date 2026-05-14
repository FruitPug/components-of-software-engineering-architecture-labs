package com.example.lab3.infrastructure.mapper;

import com.example.lab3.application.query.task_tag.TaskTagReadModel;
import com.example.lab3.domain.model.TaskTag;
import com.example.lab3.infrastructure.persistence.entity.TagEntity;
import com.example.lab3.infrastructure.persistence.entity.TaskEntity;
import com.example.lab3.infrastructure.persistence.entity.TaskTagEntity;

public class TaskTagMapper {

    public static TaskTag toDomain(TaskTagEntity e) {
        return new TaskTag(
                e.getId(),
                e.getTask().getId(),
                e.getTag().getId()
        );
    }

    public static TaskTagEntity toEntity(
            TaskTag tt,
            TaskEntity task,
            TagEntity tag
    ) {
        return TaskTagEntity.builder()
                .id(tt.getId())
                .task(task)
                .tag(tag)
                .build();
    }

    public static TaskTagReadModel toReadModel(TaskTagEntity e) {
        return TaskTagReadModel.builder()
                .id(e.getId())
                .taskId(e.getTask().getId())
                .tagId(e.getTag().getId())
                .build();
    }
}
