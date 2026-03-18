package com.example.lab2.sorting_bin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskCommentCreateDto {

    @NotNull
    private Long taskId;

    @NotNull
    private Long authorUserId;

    @NotBlank
    private String body;
}

