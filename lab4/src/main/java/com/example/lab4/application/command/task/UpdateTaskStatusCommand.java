package com.example.lab4.application.command.task;

import com.example.lab4.domain.enums.TaskStatus;

public record UpdateTaskStatusCommand(
        Long taskId,
        TaskStatus status
) {
}
