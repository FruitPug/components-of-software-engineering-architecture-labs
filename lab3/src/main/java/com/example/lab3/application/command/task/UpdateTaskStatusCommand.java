package com.example.lab3.application.command.task;

import com.example.lab3.domain.enums.TaskStatus;

public record UpdateTaskStatusCommand(
        Long taskId,
        TaskStatus status
) {
}
