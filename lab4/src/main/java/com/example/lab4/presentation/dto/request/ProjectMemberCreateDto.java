package com.example.lab4.presentation.dto.request;

import com.example.lab4.domain.enums.ProjectMemberRole;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectMemberCreateDto {

    @NotNull
    private Long projectId;

    @NotNull
    private Long userId;

    @NotNull
    private ProjectMemberRole role;
}
