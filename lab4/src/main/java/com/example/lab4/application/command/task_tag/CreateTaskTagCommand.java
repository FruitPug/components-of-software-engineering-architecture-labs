package com.example.lab4.application.command.task_tag;

public record CreateTaskTagCommand(
        Long taskId,
        Long tagId
) {}
