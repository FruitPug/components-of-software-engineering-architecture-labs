package com.example.lab1.mapper;

import com.example.lab1.dto.response.TaskTagResponseDto;
import com.example.lab1.entity.TagEntity;
import com.example.lab1.entity.TaskEntity;
import com.example.lab1.entity.TaskTagEntity;

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
