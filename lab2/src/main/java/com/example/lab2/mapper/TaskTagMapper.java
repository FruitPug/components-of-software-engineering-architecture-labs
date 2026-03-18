package com.example.lab2.mapper;

import com.example.lab2.dto.response.TaskTagResponseDto;
import com.example.lab2.entity.TagEntity;
import com.example.lab2.entity.TaskEntity;
import com.example.lab2.entity.TaskTagEntity;

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
