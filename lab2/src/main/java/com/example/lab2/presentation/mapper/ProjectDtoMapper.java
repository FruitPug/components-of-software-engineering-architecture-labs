package com.example.lab2.presentation.mapper;

import com.example.lab2.domain.model.Project;
import com.example.lab2.presentation.dto.response.ProjectResponseDto;

public class ProjectDtoMapper {

    public static ProjectResponseDto toResponseDto(Project project) {
        return ProjectResponseDto.builder()
                .name(project.getName())
                .description(project.getDescription())
                .status(project.getStatus())
                .build();
    }
}
