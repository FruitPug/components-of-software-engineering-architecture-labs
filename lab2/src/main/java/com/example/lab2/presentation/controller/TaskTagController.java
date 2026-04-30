package com.example.lab2.presentation.controller;

import com.example.lab2.application.command.task_tag.CreateTaskTagCommand;
import com.example.lab2.application.usecase.task_tag.CreateTaskTagUseCase;
import com.example.lab2.application.usecase.task_tag.GetTaskTagsUseCase;
import com.example.lab2.application.usecase.task_tag.HardDeleteTaskTagUseCase;
import com.example.lab2.presentation.dto.request.TaskTagCreateDto;
import com.example.lab2.presentation.dto.response.TaskTagResponseDto;
import com.example.lab2.presentation.mapper.TaskTagDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task-tags")
@RequiredArgsConstructor
public class TaskTagController {

    private final CreateTaskTagUseCase createUseCase;
    private final HardDeleteTaskTagUseCase deleteUseCase;
    private final GetTaskTagsUseCase getUseCase;

    @GetMapping
    public ResponseEntity<Page<TaskTagResponseDto>> getTaskTagsFiltered(
            @RequestParam(required = false) Long taskId,
            @RequestParam(required = false) Long tagId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                getUseCase.execute(taskId, tagId, pageable)
                        .map(TaskTagDtoMapper::toResponseDto)
        );
    }

    @PostMapping
    public ResponseEntity<Void> createTaskTag(@RequestBody TaskTagCreateDto dto) {
        createUseCase.execute(
                new CreateTaskTagCommand(dto.getTaskId(), dto.getTagId())
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/hard")
    public ResponseEntity<Void> hardDelete(@PathVariable Long id) {
        deleteUseCase.execute(id);
        return ResponseEntity.ok().build();
    }
}
