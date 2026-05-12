package com.example.lab3.application.command.task;

import com.example.lab3.domain.enums.TaskPriority;

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
