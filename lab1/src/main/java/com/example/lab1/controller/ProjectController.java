package com.example.lab1.controller;

import com.example.lab1.dto.request.ProjectCreateDto;
import com.example.lab1.dto.request.ProjectCreateWithOwnerDto;
import com.example.lab1.dto.request.ProjectStatusUpdateDto;
import com.example.lab1.dto.response.ProjectResponseDto;
import com.example.lab1.entity.enums.ProjectStatus;
import com.example.lab1.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<Page<ProjectResponseDto>> getProjectsFiltered(
            @RequestParam(required = false)ProjectStatus status,
            Pageable pageable
    ) {
        return projectService.getProjectsFiltered(status, pageable);
    }

    @PostMapping
    public ResponseEntity<Void> createProject(
            @Valid @RequestBody ProjectCreateDto projectCreateDto
    ) {
        return projectService.createProject(projectCreateDto);
    }

    @PostMapping("/with-owner")
    public ResponseEntity<Void> createProjectWithOwner(
            @Valid @RequestBody ProjectCreateWithOwnerDto projectCreateWithOwnerDto
    ) {
        return projectService.createProjectWithOwner(projectCreateWithOwnerDto);
    }

    @PatchMapping("/status")
    public ResponseEntity<Void> updateProjectStatus(
            @Valid @RequestBody ProjectStatusUpdateDto dto
    ) {
        return projectService.updateProjectStatus(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteProject(@PathVariable Long id) {
        return projectService.softDeleteProject(id);
    }

    @DeleteMapping("/{id}/hard")
    public ResponseEntity<Void> hardDeleteProject(@PathVariable Long id) {
        return projectService.hardDeleteProject(id);
    }
}