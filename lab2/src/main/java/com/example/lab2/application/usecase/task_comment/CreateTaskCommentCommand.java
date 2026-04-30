package com.example.lab2.application.usecase.task_comment;

public record CreateTaskCommentCommand(
        Long taskId,
        Long authorId,
        String body
) {}
