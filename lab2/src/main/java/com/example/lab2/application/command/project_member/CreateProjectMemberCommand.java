package com.example.lab2.application.command.project_member;

import com.example.lab2.domain.enums.ProjectMemberRole;

public record CreateProjectMemberCommand(
        Long projectId,
        Long userId,
        ProjectMemberRole role
) {}
