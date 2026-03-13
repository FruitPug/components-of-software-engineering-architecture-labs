package com.example.lab1.dto.request;

import com.example.lab1.entity.enums.TaskPriority;
import com.example.lab1.entity.enums.TaskStatus;
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
