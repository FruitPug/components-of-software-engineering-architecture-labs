package com.example.lab4.application.query.task;

import com.example.lab4.domain.enums.TaskPriority;
import com.example.lab4.domain.enums.TaskStatus;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TaskReadModel(
    Long id,
    Long projectId,
    Long creatorId,
    Long assigneeId,
    String title,
    String description,
    TaskStatus status,
    TaskPriority priority,
    LocalDate dueDate
) {
}
