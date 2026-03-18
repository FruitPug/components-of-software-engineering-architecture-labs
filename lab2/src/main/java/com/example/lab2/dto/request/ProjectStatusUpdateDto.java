package com.example.lab2.dto.request;

import com.example.lab2.entity.enums.ProjectStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectStatusUpdateDto {

    @NotNull
    private Long projectId;

    @NotNull
    private ProjectStatus status;
}
