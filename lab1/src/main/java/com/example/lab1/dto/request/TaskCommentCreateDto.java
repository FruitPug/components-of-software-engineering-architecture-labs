package com.example.lab1.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskCommentCreateDto {

    @NotBlank
    private Long taskId;

    @NotBlank
    private Long authorUserId;

    @NotBlank
    private String body;
}
