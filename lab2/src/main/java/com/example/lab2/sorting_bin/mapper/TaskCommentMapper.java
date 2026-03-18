package com.example.lab2.sorting_bin.mapper;

import com.example.lab2.sorting_bin.dto.request.TaskCommentCreateDto;
import com.example.lab2.sorting_bin.dto.response.TaskCommentResponseDto;
import com.example.lab2.sorting_bin.entity.TaskCommentEntity;
import com.example.lab2.sorting_bin.entity.TaskEntity;
import com.example.lab2.sorting_bin.entity.UserEntity;

import java.time.LocalDateTime;

public class TaskCommentMapper {

    public static TaskCommentEntity createTaskCommentEntity(
            TaskEntity task,
            UserEntity author,
            TaskCommentCreateDto dto
    ) {
        return TaskCommentEntity.builder()
                .task(task)
                .author(author)
                .body(dto.getBody())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deleted(false)
                .build();
    }

    public static TaskCommentResponseDto toResponseDto(TaskCommentEntity comment) {
        return TaskCommentResponseDto.builder()
                .taskId(comment.getTask().getId())
                .authorId(comment.getAuthor().getId())
                .body(comment.getBody())
                .build();
    }
}
