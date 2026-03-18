package com.example.lab2.sorting_bin.controller;

import com.example.lab2.sorting_bin.dto.request.ProjectCreateDto;
import com.example.lab2.sorting_bin.dto.request.ProjectCreateWithOwnerDto;
import com.example.lab2.sorting_bin.dto.request.ProjectStatusUpdateDto;
import com.example.lab2.sorting_bin.dto.response.ProjectResponseDto;
import com.example.lab2.sorting_bin.entity.enums.ProjectStatus;
import com.example.lab2.sorting_bin.service.ProjectService;
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
        return ResponseEntity.ok(projectService.getProjectsFiltered(status, pageable));
    }

    @PostMapping
    public ResponseEntity<Void> createProject(
            @Valid @RequestBody ProjectCreateDto projectCreateDto
    ) {
        projectService.createProject(projectCreateDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/with-owner")
    public ResponseEntity<Void> createProjectWithOwner(
            @Valid @RequestBody ProjectCreateWithOwnerDto projectCreateWithOwnerDto
    ) {
        projectService.createProjectWithOwner(projectCreateWithOwnerDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/status")
    public ResponseEntity<Void> updateProjectStatus(
            @Valid @RequestBody ProjectStatusUpdateDto dto
    ) {
        projectService.updateProjectStatus(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteProject(@PathVariable Long id) {
        projectService.softDeleteProject(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/hard")
    public ResponseEntity<Void> hardDeleteProject(@PathVariable Long id) {
        projectService.hardDeleteProject(id);
        return ResponseEntity.ok().build();
    }
}