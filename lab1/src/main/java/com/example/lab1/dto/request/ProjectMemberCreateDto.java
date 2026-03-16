package com.example.lab1.dto.request;

import com.example.lab1.entity.enums.ProjectMemberRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectMemberCreateDto {

    @NotBlank
    private Long projectId;

    @NotBlank
    private Long userId;

    @NotBlank
    private ProjectMemberRole role;
}
