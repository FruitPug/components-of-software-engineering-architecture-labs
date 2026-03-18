package com.example.lab2.sorting_bin.controller;

import com.example.lab2.sorting_bin.dto.request.TaskTagCreateDto;
import com.example.lab2.sorting_bin.dto.response.TaskTagResponseDto;
import com.example.lab2.sorting_bin.service.TaskTagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task-tags")
@RequiredArgsConstructor
public class TaskTagController {

    private final TaskTagService taskTagService;

    @GetMapping
    public ResponseEntity<Page<TaskTagResponseDto>> getTaskTagsFiltered(
            @RequestParam(required = false) Long taskId,
            @RequestParam(required = false) Long tagId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(taskTagService.getTaskTagsFiltered(taskId, tagId, pageable));
    }

    @PostMapping
    public ResponseEntity<Void> createTaskTag(
            @Valid @RequestBody TaskTagCreateDto dto
    ) {
        taskTagService.createTaskTag(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/hard")
    public ResponseEntity<Void> hardDeleteProject(@PathVariable Long id) {
        taskTagService.hardDeleteProject(id);
        return ResponseEntity.ok().build();
    }
}
