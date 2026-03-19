package com.example.lab2.sorting_bin.controller;

import com.example.lab2.sorting_bin.dto.request.ProjectMemberCreateDto;
import com.example.lab2.sorting_bin.dto.response.ProjectMemberResponseDto;
import com.example.lab2.domain.enums.ProjectMemberRole;
import com.example.lab2.sorting_bin.service.ProjectMemberService;
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
        return ResponseEntity.ok(projectMemberService.getProjectMembersFiltered(
                projectId,
                userId,
                role,
                pageable
        ));
    }

    @PostMapping
    public ResponseEntity<Void> createProjectMember(
            @Valid @RequestBody ProjectMemberCreateDto dto
    ) {
        projectMemberService.createProjectMember(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/hard")
    public ResponseEntity<Void> hardDeleteProject(@PathVariable Long id) {
        projectMemberService.hardDeleteProject(id);
        return ResponseEntity.ok().build();
    }
}
