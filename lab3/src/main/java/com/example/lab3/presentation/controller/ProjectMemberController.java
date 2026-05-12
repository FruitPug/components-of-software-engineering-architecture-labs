package com.example.lab3.presentation.controller;

import com.example.lab3.application.command.project_member.CreateProjectMemberCommand;
import com.example.lab3.application.usecase.project_member.CreateProjectMemberUseCase;
import com.example.lab3.application.usecase.project_member.GetProjectMembersUseCase;
import com.example.lab3.application.usecase.project_member.HardDeleteProjectMemberUseCase;
import com.example.lab3.presentation.mapper.ProjectMemberDtoMapper;
import com.example.lab3.presentation.dto.request.ProjectMemberCreateDto;
import com.example.lab3.presentation.dto.response.ProjectMemberResponseDto;
import com.example.lab3.domain.enums.ProjectMemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project-members")
@RequiredArgsConstructor
public class ProjectMemberController {

    private final CreateProjectMemberUseCase createUseCase;
    private final HardDeleteProjectMemberUseCase deleteUseCase;
    private final GetProjectMembersUseCase getUseCase;

    @GetMapping
    public ResponseEntity<Page<ProjectMemberResponseDto>> getProjectMembersFiltered(
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) ProjectMemberRole role,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                getUseCase.execute(projectId, userId, role, pageable)
                        .map(ProjectMemberDtoMapper::toResponseDto)
        );
    }

    @PostMapping
    public ResponseEntity<Void> createProjectMember(
            @RequestBody ProjectMemberCreateDto dto
    ) {
        createUseCase.execute(
                new CreateProjectMemberCommand(
                        dto.getProjectId(),
                        dto.getUserId(),
                        dto.getRole()
                )
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/hard")
    public ResponseEntity<Void> hardDeleteProject(@PathVariable Long id) {
        deleteUseCase.execute(id);
        return ResponseEntity.ok().build();
    }
}