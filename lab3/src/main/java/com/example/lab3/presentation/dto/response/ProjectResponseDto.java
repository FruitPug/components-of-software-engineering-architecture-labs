package com.example.lab3.presentation.dto.response;

import com.example.lab3.domain.enums.ProjectStatus;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProjectResponseDto {
    String name;
    String description;

    ProjectStatus status;
}
