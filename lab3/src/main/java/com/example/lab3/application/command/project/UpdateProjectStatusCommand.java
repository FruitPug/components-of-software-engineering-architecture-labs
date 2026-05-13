package com.example.lab3.application.command.project;

import com.example.lab3.domain.enums.ProjectStatus;

public record UpdateProjectStatusCommand(
        Long projectId,
        ProjectStatus status
) {
}
