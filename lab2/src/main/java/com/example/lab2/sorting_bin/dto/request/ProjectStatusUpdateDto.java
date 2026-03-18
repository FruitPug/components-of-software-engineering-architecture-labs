package com.example.lab2.sorting_bin.dto.request;

import com.example.lab2.sorting_bin.entity.enums.ProjectStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectStatusUpdateDto {

    @NotNull
    private Long projectId;

    @NotNull
    private ProjectStatus status;
}
