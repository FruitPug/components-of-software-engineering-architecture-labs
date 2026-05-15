package com.example.lab4.presentation.dto.request;

import com.example.lab4.domain.enums.TaskPriority;
import com.example.lab4.domain.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskCreateDto {

    @NotNull
    private Long projectId;

    @NotNull
    private Long creatorUserId;

    private Long assigneeUserId;

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private TaskStatus status;

    @NotNull
    private TaskPriority priority;

    private LocalDate dueDate;
}