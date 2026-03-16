package com.example.lab1.dto.request;

import com.example.lab1.entity.enums.TaskPriority;
import com.example.lab1.entity.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskCreateDto {

    @NotBlank
    private Long projectId;

    @NotBlank
    private Long creatorUserId;

    private Long assigneeUserId;

    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private TaskStatus status;

    @NotBlank
    private TaskPriority priority;

    private LocalDate dueDate;
}
