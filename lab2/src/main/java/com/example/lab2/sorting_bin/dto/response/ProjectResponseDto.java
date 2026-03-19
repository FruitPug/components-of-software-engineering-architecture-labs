package com.example.lab2.sorting_bin.dto.response;

import com.example.lab2.domain.enums.ProjectStatus;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProjectResponseDto {
    String name;
    String description;

    ProjectStatus status;
}
