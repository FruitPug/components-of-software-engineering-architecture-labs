package com.example.lab2.presentation.mapper;

import com.example.lab2.application.usecase.task.CreateTaskCommand;
import com.example.lab2.domain.model.Task;
import com.example.lab2.presentation.dto.request.TaskCreateDto;
import com.example.lab2.presentation.dto.response.TaskResponseDto;

public class TaskDtoMapper {

    private TaskDtoMapper() {}

    public static CreateTaskCommand toCommand(TaskCreateDto dto) {
        return new CreateTaskCommand(
                dto.getProjectId(),
                dto.getCreatorUserId(),
                dto.getAssigneeUserId(),
                dto.getTitle(),
                dto.getDescription(),
                dto.getPriority(),
                dto.getDueDate()
        );
    }

    public static TaskResponseDto toResponseDto(Task task) {
        return TaskResponseDto.builder()
                .id(task.getId())
                .projectId(task.getProjectId())
                .creatorUserId(task.getCreatorId())
                .assigneeUserId(task.getAssigneeId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .build();
    }
}
