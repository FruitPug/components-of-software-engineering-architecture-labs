package com.example.lab4.presentation.mapper;

import com.example.lab4.application.query.project.ProjectReadModel;
import com.example.lab4.domain.model.Project;
import com.example.lab4.presentation.dto.response.ProjectResponseDto;

public class ProjectDtoMapper {

    public static ProjectResponseDto toResponseDto(Project project) {
        return ProjectResponseDto.builder()
                .name(project.getName())
                .description(project.getDescription())
                .status(project.getStatus())
                .build();
    }

    public static ProjectResponseDto toResponseDto(ProjectReadModel project) {
        return ProjectResponseDto.builder()
                .name(project.name())
                .description(project.description())
                .status(project.status())
                .build();
    }
}
