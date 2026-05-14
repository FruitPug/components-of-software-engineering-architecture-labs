package com.example.lab3.application.query.task_tag;

import lombok.Builder;

@Builder
public record TaskTagReadModel(
        Long id,
        Long taskId,
        Long tagId
) {
}
