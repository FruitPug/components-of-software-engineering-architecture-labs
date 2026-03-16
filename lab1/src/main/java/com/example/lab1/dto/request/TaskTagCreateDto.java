package com.example.lab1.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskTagCreateDto {

    @NotBlank
    private Long taskId;

    @NotBlank
    private Long tagId;
}
