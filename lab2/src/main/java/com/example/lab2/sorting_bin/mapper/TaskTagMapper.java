package com.example.lab2.sorting_bin.mapper;

import com.example.lab2.sorting_bin.dto.response.TaskTagResponseDto;
import com.example.lab2.sorting_bin.entity.TagEntity;
import com.example.lab2.infrastructure.persistence.entity.TaskEntity;
import com.example.lab2.sorting_bin.entity.TaskTagEntity;

public class TaskTagMapper {

    public static TaskTagEntity createTaskTagEntity(
            TaskEntity task,
            TagEntity tag
    ) {
        return TaskTagEntity.builder()
                .task(task)
                .tag(tag)
                .build();
    }

    public static TaskTagResponseDto toResponseDto(TaskTagEntity taskTag) {
        return TaskTagResponseDto.builder()
                .taskId(taskTag.getTask().getId())
                .tagId(taskTag.getTag().getId())
                .build();
    }
}
