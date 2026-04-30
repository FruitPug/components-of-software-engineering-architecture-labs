package com.example.lab2.application.command.task_tag;

public record CreateTaskTagCommand(
        Long taskId,
        Long tagId
) {}
