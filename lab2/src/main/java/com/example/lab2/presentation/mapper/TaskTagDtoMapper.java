package com.example.lab2.presentation.mapper;

import com.example.lab2.domain.model.TaskTag;
import com.example.lab2.presentation.dto.response.TaskTagResponseDto;

public class TaskTagDtoMapper {

    public static TaskTagResponseDto toResponseDto(TaskTag tt) {
        return TaskTagResponseDto.builder()
                .taskId(tt.getTaskId())
                .tagId(tt.getTagId())
                .build();
    }
}
