package com.example.lab1.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskTagCreateDto {

    @NotNull
    private Long taskId;

    @NotNull
    private Long tagId;
}
