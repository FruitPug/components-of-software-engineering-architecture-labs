package com.example.lab4.application.command.project_member;

import com.example.lab4.domain.enums.ProjectMemberRole;

public record CreateProjectMemberCommand(
        Long projectId,
        Long userId,
        ProjectMemberRole role
) {}
