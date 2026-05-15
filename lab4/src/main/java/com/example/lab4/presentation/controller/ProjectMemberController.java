package com.example.lab4.presentation.controller;

import com.example.lab4.application.command.project_member.CreateProjectMemberCommand;
import com.example.lab4.application.command.project_member.CreateProjectMemberCommandHandler;
import com.example.lab4.application.command.project_member.HardDeleteProjectMemberCommand;
import com.example.lab4.application.query.project_member.GetProjectMembersQuery;
import com.example.lab4.application.query.project_member.GetProjectMembersQueryHandler;
import com.example.lab4.application.command.project_member.HardDeleteProjectMemberCommandHandler;
import com.example.lab4.presentation.mapper.ProjectMemberDtoMapper;
import com.example.lab4.presentation.dto.request.ProjectMemberCreateDto;
import com.example.lab4.presentation.dto.response.ProjectMemberResponseDto;
import com.example.lab4.domain.enums.ProjectMemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project-members")
@RequiredArgsConstructor
public class ProjectMemberController {

    private final CreateProjectMemberCommandHandler createUseCase;
    private final HardDeleteProjectMemberCommandHandler deleteUseCase;
    private final GetProjectMembersQueryHandler getUseCase;

    @GetMapping
    public ResponseEntity<Page<ProjectMemberResponseDto>> getProjectMembersFiltered(
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) ProjectMemberRole role,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                getUseCase.handle(new GetProjectMembersQuery(projectId, userId, role, pageable))
                        .map(ProjectMemberDtoMapper::toResponseDto)
        );
    }

    @PostMapping
    public ResponseEntity<Void> createProjectMember(
            @RequestBody ProjectMemberCreateDto dto
    ) {
        createUseCase.handle(
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
        deleteUseCase.handle(new HardDeleteProjectMemberCommand(id));
        return ResponseEntity.ok().build();
    }
}