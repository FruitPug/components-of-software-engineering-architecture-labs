package com.example.lab2.presentation.controller;

import com.example.lab2.application.command.task_comment.CreateTaskCommentCommand;
import com.example.lab2.application.usecase.task_comment.CreateTaskCommentUseCase;
import com.example.lab2.application.usecase.task_comment.GetTaskCommentsUseCase;
import com.example.lab2.application.usecase.task_comment.SoftDeleteTaskCommentUseCase;
import com.example.lab2.presentation.mapper.TaskCommentDtoMapper;
import com.example.lab2.presentation.dto.request.TaskCommentCreateDto;
import com.example.lab2.presentation.dto.response.TaskCommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task-comments")
@RequiredArgsConstructor
public class TaskCommentController {

    private final CreateTaskCommentUseCase createUseCase;
    private final SoftDeleteTaskCommentUseCase deleteUseCase;
    private final GetTaskCommentsUseCase getUseCase;

    @GetMapping
    public ResponseEntity<Page<TaskCommentResponseDto>> getCommentsFiltered(
            @RequestParam(required = false) Long taskId,
            @RequestParam(required = false) Long userId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                getUseCase.execute(taskId, userId, pageable)
                        .map(TaskCommentDtoMapper::toResponseDto)
        );
    }

    @PostMapping
    public ResponseEntity<Void> createComment(
            @RequestBody TaskCommentCreateDto dto
    ) {
        createUseCase.execute(
                new CreateTaskCommentCommand(
                        dto.getTaskId(),
                        dto.getAuthorUserId(),
                        dto.getBody()
                )
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteComment(@PathVariable Long id) {
        deleteUseCase.execute(id);
        return ResponseEntity.ok().build();
    }
}