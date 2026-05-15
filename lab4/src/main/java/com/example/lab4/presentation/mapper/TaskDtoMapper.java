package com.example.lab4.presentation.mapper;

import com.example.lab4.application.command.task.CreateTaskCommand;
import com.example.lab4.application.query.task.TaskReadModel;
import com.example.lab4.domain.model.Task;
import com.example.lab4.presentation.dto.request.TaskCreateDto;
import com.example.lab4.presentation.dto.response.TaskResponseDto;

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

    public static TaskResponseDto toResponseDto(TaskReadModel task) {
        return TaskResponseDto.builder()
                .id(task.id())
                .projectId(task.projectId())
                .creatorUserId(task.creatorId())
                .assigneeUserId(task.assigneeId())
                .title(task.title())
                .description(task.description())
                .status(task.status())
                .priority(task.priority())
                .dueDate(task.dueDate())
                .build();
    }
}
