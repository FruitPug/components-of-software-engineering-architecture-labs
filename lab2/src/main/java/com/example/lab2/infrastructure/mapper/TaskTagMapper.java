package com.example.lab2.infrastructure.mapper;

import com.example.lab2.domain.model.TaskTag;
import com.example.lab2.infrastructure.persistence.entity.TagEntity;
import com.example.lab2.infrastructure.persistence.entity.TaskEntity;
import com.example.lab2.infrastructure.persistence.entity.TaskTagEntity;

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
}
