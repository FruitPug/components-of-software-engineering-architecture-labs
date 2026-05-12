package com.example.lab3.presentation.controller;

import com.example.lab3.application.command.project.CreateProjectWithOwnerCommand;
import com.example.lab3.application.usecase.project.*;
import com.example.lab3.presentation.mapper.ProjectDtoMapper;
import com.example.lab3.presentation.dto.request.ProjectCreateDto;
import com.example.lab3.presentation.dto.request.ProjectCreateWithOwnerDto;
import com.example.lab3.presentation.dto.request.ProjectStatusUpdateDto;
import com.example.lab3.presentation.dto.response.ProjectResponseDto;
import com.example.lab3.domain.enums.ProjectStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final CreateProjectUseCase createProjectUseCase;
    private final CreateProjectWithOwnerUseCase createProjectWithOwnerUseCase;
    private final UpdateProjectStatusUseCase updateStatusUseCase;
    private final SoftDeleteProjectUseCase softDeleteUseCase;
    private final HardDeleteProjectUseCase hardDeleteUseCase;
    private final GetProjectsUseCase getProjectsUseCase;

    @GetMapping
    public ResponseEntity<Page<ProjectResponseDto>> getProjectsFiltered(
            @RequestParam(required = false) ProjectStatus status,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                getProjectsUseCase.execute(status, pageable)
                        .map(ProjectDtoMapper::toResponseDto)
        );
    }

    @PostMapping
    public ResponseEntity<Void> createProject(@RequestBody ProjectCreateDto dto) {
        createProjectUseCase.execute(dto.getName(), dto.getDescription());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/with-owner")
    public ResponseEntity<Void> createProjectWithOwner(@RequestBody ProjectCreateWithOwnerDto dto) {
        createProjectWithOwnerUseCase.execute(
                new CreateProjectWithOwnerCommand(
                        dto.getName(),
                        dto.getDescription(),
                        dto.getOwnerId()
                )
        );
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/status")
    public ResponseEntity<Void> updateProjectStatus(@RequestBody ProjectStatusUpdateDto dto) {
        updateStatusUseCase.execute(dto.getProjectId(), dto.getStatus());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteProject(@PathVariable Long id) {
        softDeleteUseCase.execute(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/hard")
    public ResponseEntity<Void> hardDeleteProject(@PathVariable Long id) {
        hardDeleteUseCase.execute(id);
        return ResponseEntity.ok().build();
    }
}