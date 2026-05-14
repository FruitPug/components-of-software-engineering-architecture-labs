package com.example.lab3.presentation.mapper;

import com.example.lab3.application.query.task_tag.TaskTagReadModel;
import com.example.lab3.domain.model.TaskTag;
import com.example.lab3.presentation.dto.response.TaskTagResponseDto;

public class TaskTagDtoMapper {

    public static TaskTagResponseDto toResponseDto(TaskTag tt) {
        return TaskTagResponseDto.builder()
                .taskId(tt.getTaskId())
                .tagId(tt.getTagId())
                .build();
    }

    public static TaskTagResponseDto toResponseDto(TaskTagReadModel tt) {
        return TaskTagResponseDto.builder()
                .taskId(tt.taskId())
                .tagId(tt.tagId())
                .build();
    }
}
