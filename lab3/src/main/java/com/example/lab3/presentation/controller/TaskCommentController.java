package com.example.lab3.presentation.controller;

import com.example.lab3.application.command.task_comment.CreateTaskCommentCommand;
import com.example.lab3.application.command.task_comment.CreateTaskCommentCommandHandler;
import com.example.lab3.application.command.task_comment.SoftDeleteTaskCommentCommand;
import com.example.lab3.application.query.task_comment.GetTaskCommentsQuery;
import com.example.lab3.application.query.task_comment.GetTaskCommentsQueryHandler;
import com.example.lab3.application.command.task_comment.SoftDeleteTaskCommentCommandHandler;
import com.example.lab3.presentation.mapper.TaskCommentDtoMapper;
import com.example.lab3.presentation.dto.request.TaskCommentCreateDto;
import com.example.lab3.presentation.dto.response.TaskCommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task-comments")
@RequiredArgsConstructor
public class TaskCommentController {

    private final CreateTaskCommentCommandHandler createUseCase;
    private final SoftDeleteTaskCommentCommandHandler deleteUseCase;
    private final GetTaskCommentsQueryHandler getUseCase;

    @GetMapping
    public ResponseEntity<Page<TaskCommentResponseDto>> getCommentsFiltered(
            @RequestParam(required = false) Long taskId,
            @RequestParam(required = false) Long userId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                getUseCase.handle(new GetTaskCommentsQuery(taskId, userId, pageable))
                        .map(TaskCommentDtoMapper::toResponseDto)
        );
    }

    @PostMapping
    public ResponseEntity<Void> createComment(
            @RequestBody TaskCommentCreateDto dto
    ) {
        createUseCase.handle(
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
        deleteUseCase.handle(new SoftDeleteTaskCommentCommand(id));
        return ResponseEntity.ok().build();
    }
}