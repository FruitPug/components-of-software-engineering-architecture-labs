package com.example.lab2.application.usecase.task;

import com.example.lab2.domain.enums.TaskPriority;

import java.time.LocalDate;

public record CreateTaskCommand(
        Long projectId,
        Long creatorId,
        Long assigneeId,
        String title,
        String description,
        TaskPriority priority,
        LocalDate dueDate
) {
}
