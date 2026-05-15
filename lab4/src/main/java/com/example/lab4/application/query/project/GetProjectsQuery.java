package com.example.lab4.application.query.project;

import com.example.lab4.domain.enums.ProjectStatus;
import org.springframework.data.domain.Pageable;

public record GetProjectsQuery(
        ProjectStatus status,
        Pageable pageable
) {
}
