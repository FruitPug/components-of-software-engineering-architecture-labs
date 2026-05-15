package com.example.lab4.application.command.task;

public record ReassignTaskCommand (
        Long taskId,
        Long newAssigneeId
) {
}
