package com.example.lab4.presentation.mapper;

import com.example.lab4.application.query.task_comment.TaskCommentReadModel;
import com.example.lab4.domain.model.TaskComment;
import com.example.lab4.presentation.dto.response.TaskCommentResponseDto;

public class TaskCommentDtoMapper {

    public static TaskCommentResponseDto toResponseDto(TaskComment c) {
        return TaskCommentResponseDto.builder()
                .taskId(c.getTaskId())
                .authorId(c.getAuthorId())
                .body(c.getBody())
                .build();
    }

    public static TaskCommentResponseDto toResponseDto(TaskCommentReadModel c) {
        return TaskCommentResponseDto.builder()
                .taskId(c.taskId())
                .authorId(c.authorId())
                .body(c.body())
                .build();
    }
}
