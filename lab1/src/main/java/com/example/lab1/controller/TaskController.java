package com.example.lab1.controller;

import com.example.lab1.dto.request.TaskCreateDto;
import com.example.lab1.dto.request.TaskReassignDto;
import com.example.lab1.dto.request.TaskStatusUpdateDto;
import com.example.lab1.dto.response.TaskResponseDto;
import com.example.lab1.entity.enums.TaskPriority;
import com.example.lab1.entity.enums.TaskStatus;
import com.example.lab1.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<Page<TaskResponseDto>> getTasksFiltered(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) Long assigneeId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(taskService.getTasksFiltered(
                status,
                priority,
                projectId,
                assigneeId,
                pageable
        ));
    }

    @PostMapping
    public ResponseEntity<Void> createTask(
            @Valid @RequestBody TaskCreateDto dto
    ) {
        taskService.createTask(dto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/status")
    public ResponseEntity<Void> updateTaskStatus(
            @Valid @RequestBody TaskStatusUpdateDto dto
    ) {
        taskService.updateTaskStatus(dto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/assignee")
    public ResponseEntity<Void> reassignTask(
            @Valid @RequestBody TaskReassignDto dto
    ) {
        taskService.reassignTask(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteTask(@PathVariable Long id) {
        taskService.softDeleteTask(id);
        return ResponseEntity.ok().build();
    }
}
