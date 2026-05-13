package com.example.lab3.application.query.project;

import com.example.lab3.domain.enums.ProjectStatus;
import org.springframework.data.domain.Pageable;

public record GetProjectsQuery(
        ProjectStatus status,
        Pageable pageable
) {
}
