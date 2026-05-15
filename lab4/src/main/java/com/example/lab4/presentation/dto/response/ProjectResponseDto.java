package com.example.lab4.presentation.dto.response;

import com.example.lab4.domain.enums.ProjectStatus;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProjectResponseDto {
    String name;
    String description;

    ProjectStatus status;
}
