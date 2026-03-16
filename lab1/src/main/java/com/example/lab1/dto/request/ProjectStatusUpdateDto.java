package com.example.lab1.dto.request;

import com.example.lab1.entity.enums.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectStatusUpdateDto {

    @NotBlank
    private Long projectId;

    @NotBlank
    private ProjectStatus status;
}
