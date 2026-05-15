package com.example.lab4.application.query.task_tag;

import org.springframework.data.domain.Pageable;

public record GetTaskTagsQuery(
        Long taskId,
        Long tagId,
        Pageable pageable
) {
}
