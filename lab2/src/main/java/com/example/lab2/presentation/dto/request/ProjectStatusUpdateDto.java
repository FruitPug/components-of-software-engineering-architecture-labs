package com.example.lab2.presentation.dto.request;

import com.example.lab2.domain.enums.ProjectStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectStatusUpdateDto {

    @NotNull
    private Long projectId;

    @NotNull
    private ProjectStatus status;
}
