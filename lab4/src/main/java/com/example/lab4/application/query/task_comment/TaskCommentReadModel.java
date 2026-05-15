package com.example.lab4.application.query.task_comment;

import lombok.Builder;

@Builder
public record TaskCommentReadModel(
        Long id,
        Long taskId,
        Long authorId,
        String body
) {
}
