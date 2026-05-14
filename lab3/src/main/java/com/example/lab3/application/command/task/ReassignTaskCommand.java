package com.example.lab3.application.command.task;

public record ReassignTaskCommand (
        Long taskId,
        Long newAssigneeId
) {
}
