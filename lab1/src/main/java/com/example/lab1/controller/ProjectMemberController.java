package com.example.lab1.controller;

import com.example.lab1.dto.request.ProjectMemberCreateDto;
import com.example.lab1.dto.response.ProjectMemberResponseDto;
import com.example.lab1.entity.enums.ProjectMemberRole;
import com.example.lab1.service.ProjectMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project-members")
@RequiredArgsConstructor
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    @GetMapping
    public ResponseEntity<Page<ProjectMemberResponseDto>> getProjectMembersFiltered(
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) ProjectMemberRole role,
            Pageable pageable
    ) {
        return projectMemberService.getProjectMembersFiltered(projectId, userId, role, pageable);
    }

    @PostMapping
    public ResponseEntity<Void> createProjectMember(
            @Valid @RequestBody ProjectMemberCreateDto dto
    ) {
        return projectMemberService.createProjectMember(dto);
    }

    @DeleteMapping("/{id}/hard")
    public ResponseEntity<Void> hardDeleteProject(@PathVariable Long id) {
        return projectMemberService.hardDeleteProject(id);
    }
}
