package com.example.lab2.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskTagCreateDto {

    @NotNull
    private Long taskId;

    @NotNull
    private Long tagId;
}
