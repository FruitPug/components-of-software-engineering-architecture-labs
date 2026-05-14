package com.example.lab3.presentation.controller;

import com.example.lab3.application.command.task.*;
import com.example.lab3.application.query.task.GetTasksQuery;
import com.example.lab3.application.query.task.GetTasksQueryHandler;
import com.example.lab3.presentation.mapper.TaskDtoMapper;
import com.example.lab3.presentation.dto.request.TaskCreateDto;
import com.example.lab3.presentation.dto.request.TaskReassignDto;
import com.example.lab3.presentation.dto.request.TaskStatusUpdateDto;
import com.example.lab3.presentation.dto.response.TaskResponseDto;
import com.example.lab3.domain.enums.TaskPriority;
import com.example.lab3.domain.enums.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final CreateTaskCommandHandler createUseCase;
    private final UpdateTaskStatusCommandHandler statusUseCase;
    private final ReassignTaskCommandHandler reassignUseCase;
    private final SoftDeleteTaskCommandHandler deleteUseCase;
    private final GetTasksQueryHandler getUseCase;

    @GetMapping
    public ResponseEntity<Page<TaskResponseDto>> getTasksFiltered(
            TaskStatus status,
            TaskPriority priority,
            Long projectId,
            Long assigneeId,
            Pageable pageable
    ) {
        GetTasksQuery query = new GetTasksQuery(status, priority, projectId, assigneeId, pageable);

        Page<TaskResponseDto> page = getUseCase.handle(query)
                .map(TaskDtoMapper::toResponseDto);

        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<Void> createTask(@RequestBody TaskCreateDto dto) {
        createUseCase.handle(TaskDtoMapper.toCommand(dto));
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/status")
    public ResponseEntity<Void> updateTaskStatus(@RequestBody TaskStatusUpdateDto dto) {
        statusUseCase.handle(new UpdateTaskStatusCommand(dto.getTaskId(), dto.getStatus()));
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/assignee")
    public ResponseEntity<Void> reassignTask(@RequestBody TaskReassignDto dto) {
        reassignUseCase.handle(new ReassignTaskCommand(dto.getTaskId(), dto.getNewAssigneeUserId()));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteTask(@PathVariable Long id) {
        deleteUseCase.handle(new SoftDeleteTaskCommand(id));
        return ResponseEntity.ok().build();
    }
}