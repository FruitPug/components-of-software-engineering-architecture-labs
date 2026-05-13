package com.example.lab3.application.query.project;

import com.example.lab3.domain.enums.ProjectStatus;

public record ProjectReadModel(
        Long id,
        String name,
        String description,
        ProjectStatus status
) {
}
