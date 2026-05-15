package com.example.lab4.domain.model;

import com.example.lab4.domain.error.DomainError;
import lombok.Getter;
import lombok.Setter;

@Getter
public class TaskTag {

    @Setter
    private Long id;
    private final Long taskId;
    private final Long tagId;

    public TaskTag(Long taskId, Long tagId) {
        if (taskId == null || tagId == null) {
            throw new DomainError("INVALID_TASK_TAG_REFERENCE");
        }

        this.taskId = taskId;
        this.tagId = tagId;
    }

    public TaskTag(Long id, Long taskId, Long tagId) {
        this.id = id;
        this.taskId = taskId;
        this.tagId = tagId;
    }
}
