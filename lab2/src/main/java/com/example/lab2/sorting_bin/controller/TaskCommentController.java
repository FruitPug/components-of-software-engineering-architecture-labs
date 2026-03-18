package com.example.lab2.sorting_bin.controller;

import com.example.lab2.sorting_bin.dto.request.TaskCommentCreateDto;
import com.example.lab2.sorting_bin.dto.response.TaskCommentResponseDto;
import com.example.lab2.sorting_bin.service.TaskCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task-comments")
@RequiredArgsConstructor
public class TaskCommentController {

    private final TaskCommentService taskCommentService;

    @GetMapping
    public ResponseEntity<Page<TaskCommentResponseDto>> getCommentsFiltered(
            @RequestParam(required = false) Long taskId,
            @RequestParam(required = false) Long userId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(taskCommentService.getCommentsFiltered(taskId, userId, pageable));
    }

    @PostMapping
    public ResponseEntity<Void> createComment(
            @Valid @RequestBody TaskCommentCreateDto dto
    ) {
        taskCommentService.createComment(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteComment(@PathVariable Long id) {
        taskCommentService.softDeleteComment(id);
        return ResponseEntity.ok().build();
    }
}
