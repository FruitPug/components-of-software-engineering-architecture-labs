package com.example.lab3.presentation.mapper;

import com.example.lab3.domain.model.TaskComment;
import com.example.lab3.presentation.dto.response.TaskCommentResponseDto;

public class TaskCommentDtoMapper {

    public static TaskCommentResponseDto toResponseDto(TaskComment c) {
        return TaskCommentResponseDto.builder()
                .taskId(c.getTaskId())
                .authorId(c.getAuthorId())
                .body(c.getBody())
                .build();
    }
}
