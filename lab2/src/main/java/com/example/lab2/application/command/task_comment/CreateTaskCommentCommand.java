package com.example.lab2.application.command.task_comment;

public record CreateTaskCommentCommand(
        Long taskId,
        Long authorId,
        String body
) {}
