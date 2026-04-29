package com.example.lab2.presentation.controller;

import com.example.lab2.application.usecase.task.*;
import com.example.lab2.presentation.mapper.TaskDtoMapper;
import com.example.lab2.presentation.dto.request.TaskCreateDto;
import com.example.lab2.presentation.dto.request.TaskReassignDto;
import com.example.lab2.presentation.dto.request.TaskStatusUpdateDto;
import com.example.lab2.presentation.dto.response.TaskResponseDto;
import com.example.lab2.domain.enums.TaskPriority;
import com.example.lab2.domain.enums.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final CreateTaskUseCase createUseCase;
    private final UpdateTaskStatusUseCase statusUseCase;
    private final ReassignTaskUseCase reassignUseCase;
    private final SoftDeleteTaskUseCase deleteUseCase;
    private final GetTasksUseCase getUseCase;

    @GetMapping
    public ResponseEntity<Page<TaskResponseDto>> getTasksFiltered(
            TaskStatus status,
            TaskPriority priority,
            Long projectId,
            Long assigneeId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                getUseCase.execute(status, priority, projectId, assigneeId, pageable)
                        .map(TaskDtoMapper::toResponseDto)
        );
    }

    @PostMapping
    public ResponseEntity<Void> createTask(@RequestBody TaskCreateDto dto) {
        createUseCase.execute(TaskDtoMapper.toCommand(dto));
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/status")
    public ResponseEntity<Void> updateTaskStatus(@RequestBody TaskStatusUpdateDto dto) {
        statusUseCase.execute(dto.getTaskId(), dto.getStatus());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/assignee")
    public ResponseEntity<Void> reassignTask(@RequestBody TaskReassignDto dto) {
        reassignUseCase.execute(dto.getTaskId(), dto.getNewAssigneeUserId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteTask(@PathVariable Long id) {
        deleteUseCase.execute(id);
        return ResponseEntity.ok().build();
    }
}