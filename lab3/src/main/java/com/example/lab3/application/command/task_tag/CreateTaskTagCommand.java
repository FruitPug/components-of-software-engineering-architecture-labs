package com.example.lab3.application.command.task_tag;

public record CreateTaskTagCommand(
        Long taskId,
        Long tagId
) {}
