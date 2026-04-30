package com.example.lab2.application.usecase.task_tag;

public record CreateTaskTagCommand(
        Long taskId,
        Long tagId
) {}
