package com.example.lab3.application.command.project_member;

import com.example.lab3.domain.enums.ProjectMemberRole;

public record CreateProjectMemberCommand(
        Long projectId,
        Long userId,
        ProjectMemberRole role
) {}
