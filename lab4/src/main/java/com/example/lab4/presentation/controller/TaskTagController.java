package com.example.lab4.presentation.controller;

import com.example.lab4.application.command.task_tag.CreateTaskTagCommand;
import com.example.lab4.application.command.task_tag.CreateTaskTagCommandHandler;
import com.example.lab4.application.command.task_tag.HardDeleteTaskTagCommand;
import com.example.lab4.application.query.task_tag.GetTaskTagsQuery;
import com.example.lab4.application.query.task_tag.GetTaskTagsQueryHandler;
import com.example.lab4.application.command.task_tag.HardDeleteTaskTagCommandHandler;
import com.example.lab4.presentation.dto.request.TaskTagCreateDto;
import com.example.lab4.presentation.dto.response.TaskTagResponseDto;
import com.example.lab4.presentation.mapper.TaskTagDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task-tags")
@RequiredArgsConstructor
public class TaskTagController {

    private final CreateTaskTagCommandHandler createUseCase;
    private final HardDeleteTaskTagCommandHandler deleteUseCase;
    private final GetTaskTagsQueryHandler getUseCase;

    @GetMapping
    public ResponseEntity<Page<TaskTagResponseDto>> getTaskTagsFiltered(
            @RequestParam(required = false) Long taskId,
            @RequestParam(required = false) Long tagId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                getUseCase.handle(new GetTaskTagsQuery(taskId, tagId, pageable))
                        .map(TaskTagDtoMapper::toResponseDto)
        );
    }

    @PostMapping
    public ResponseEntity<Void> createTaskTag(@RequestBody TaskTagCreateDto dto) {
        createUseCase.handle(
                new CreateTaskTagCommand(dto.getTaskId(), dto.getTagId())
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/hard")
    public ResponseEntity<Void> hardDelete(@PathVariable Long id) {
        deleteUseCase.handle(new HardDeleteTaskTagCommand(id));
        return ResponseEntity.ok().build();
    }
}
