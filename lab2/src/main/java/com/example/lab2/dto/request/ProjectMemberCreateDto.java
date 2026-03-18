package com.example.lab2.dto.request;

import com.example.lab2.entity.enums.ProjectMemberRole;
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
