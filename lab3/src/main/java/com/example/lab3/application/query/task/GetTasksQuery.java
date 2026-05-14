package com.example.lab3.application.query.task;

import com.example.lab3.domain.enums.TaskPriority;
import com.example.lab3.domain.enums.TaskStatus;
import org.springframework.data.domain.Pageable;

public record GetTasksQuery(
        TaskStatus status,
        TaskPriority priority,
        Long projectId,
        Long assigneeId,
        Pageable pageable
) {
}
