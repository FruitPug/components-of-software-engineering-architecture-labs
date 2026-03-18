package com.example.lab2.sorting_bin.mapper;

import com.example.lab2.sorting_bin.dto.request.TaskCreateDto;
import com.example.lab2.sorting_bin.dto.response.TaskResponseDto;
import com.example.lab2.sorting_bin.entity.ProjectEntity;
import com.example.lab2.sorting_bin.entity.TaskEntity;
import com.example.lab2.infrastructure.persistence.entity.UserEntity;

import java.time.LocalDateTime;

public class TaskMapper {

    public static TaskEntity createTaskEntity(
            ProjectEntity project,
            UserEntity creator,
            UserEntity assignee,
            TaskCreateDto dto
    ) {
        return TaskEntity.builder()
                .project(project)
                .creator(creator)
                .assignee(assignee)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .priority(dto.getPriority())
                .dueDate(dto.getDueDate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deleted(false)
                .build();
    }

    public static TaskResponseDto toResponseDto(TaskEntity task) {
        return TaskResponseDto.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .projectId(task.getProject().getId())
                .creatorUserId(task.getCreator().getId())
                .assigneeUserId(task.getAssignee().getId())
                .build();
    }
}
