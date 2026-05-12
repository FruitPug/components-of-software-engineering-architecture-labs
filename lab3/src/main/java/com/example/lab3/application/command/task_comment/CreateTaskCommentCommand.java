package com.example.lab3.application.command.task_comment;

public record CreateTaskCommentCommand(
        Long taskId,
        Long authorId,
        String body
) {}
