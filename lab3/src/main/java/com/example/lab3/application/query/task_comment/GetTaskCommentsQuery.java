package com.example.lab3.application.query.task_comment;

import org.springframework.data.domain.Pageable;

public record GetTaskCommentsQuery(
        Long taskId,
        Long userId,
        Pageable pageable
) {
}
