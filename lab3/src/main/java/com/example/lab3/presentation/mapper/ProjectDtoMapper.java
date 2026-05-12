package com.example.lab3.presentation.mapper;

import com.example.lab3.domain.model.Project;
import com.example.lab3.presentation.dto.response.ProjectResponseDto;

public class ProjectDtoMapper {

    public static ProjectResponseDto toResponseDto(Project project) {
        return ProjectResponseDto.builder()
                .name(project.getName())
                .description(project.getDescription())
                .status(project.getStatus())
                .build();
    }
}
