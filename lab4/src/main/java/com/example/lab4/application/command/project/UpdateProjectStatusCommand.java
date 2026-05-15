package com.example.lab4.application.command.project;

import com.example.lab4.domain.enums.ProjectStatus;

public record UpdateProjectStatusCommand(
        Long projectId,
        ProjectStatus status
) {
}
