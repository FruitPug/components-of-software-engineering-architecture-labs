package com.example.lab4.application.query.project;

import com.example.lab4.domain.enums.ProjectStatus;
import lombok.Builder;

@Builder
public record ProjectReadModel(
        Long id,
        String name,
        String description,
        ProjectStatus status
) {
}
