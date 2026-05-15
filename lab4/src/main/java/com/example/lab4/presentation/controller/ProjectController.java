package com.example.lab4.presentation.controller;

import com.example.lab4.application.command.project.*;
import com.example.lab4.application.query.project.GetProjectsQuery;
import com.example.lab4.application.query.project.GetProjectsQueryHandler;
import com.example.lab4.presentation.mapper.ProjectDtoMapper;
import com.example.lab4.presentation.dto.request.ProjectCreateDto;
import com.example.lab4.presentation.dto.request.ProjectCreateWithOwnerDto;
import com.example.lab4.presentation.dto.request.ProjectStatusUpdateDto;
import com.example.lab4.presentation.dto.response.ProjectResponseDto;
import com.example.lab4.domain.enums.ProjectStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final CreateProjectCommandHandler createProjectCommandHandler;
    private final CreateProjectWithOwnerCommandHandler createProjectWithOwnerCommandHandler;
    private final UpdateProjectStatusCommandHandler updateStatusCommandHandler;
    private final SoftDeleteProjectCommandHandler softDeleteCommandHandler;
    private final HardDeleteProjectCommandHandler hardDeleteCommandHandler;
    private final GetProjectsQueryHandler getProjectsQueryHandler;

    @GetMapping
    public ResponseEntity<Page<ProjectResponseDto>> getProjectsFiltered(
            @RequestParam(required = false) ProjectStatus status,
            Pageable pageable
    ) {

        GetProjectsQuery query = new GetProjectsQuery(status, pageable);

        Page<ProjectResponseDto> response =
                getProjectsQueryHandler.handle(query)
                        .map(ProjectDtoMapper::toResponseDto);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> createProject(
            @RequestBody ProjectCreateDto dto
    ) {

        createProjectCommandHandler.handle(
                new CreateProjectCommand(
                        dto.getName(),
                        dto.getDescription()
                )
        );

        return ResponseEntity.ok().build();
    }

    @PostMapping("/with-owner")
    public ResponseEntity<Void> createProjectWithOwner(
            @RequestBody ProjectCreateWithOwnerDto dto
    ) {

        createProjectWithOwnerCommandHandler.handle(
                new CreateProjectWithOwnerCommand(
                        dto.getName(),
                        dto.getDescription(),
                        dto.getOwnerId()
                )
        );

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/status")
    public ResponseEntity<Void> updateProjectStatus(
            @RequestBody ProjectStatusUpdateDto dto
    ) {

        updateStatusCommandHandler.handle(
                new UpdateProjectStatusCommand(
                        dto.getProjectId(),
                        dto.getStatus()
                )
        );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteProject(
            @PathVariable Long id
    ) {

        softDeleteCommandHandler.handle(
                new SoftDeleteProjectCommand(id)
        );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/hard")
    public ResponseEntity<Void> hardDeleteProject(
            @PathVariable Long id
    ) {

        hardDeleteCommandHandler.handle(
                new HardDeleteProjectCommand(id)
        );

        return ResponseEntity.ok().build();
    }
}