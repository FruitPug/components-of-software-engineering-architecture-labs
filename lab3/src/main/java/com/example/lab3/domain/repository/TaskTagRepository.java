package com.example.lab3.domain.repository;

import com.example.lab3.domain.model.TaskTag;

public interface TaskTagRepository {

    TaskTag save(TaskTag taskTag);

    boolean exists(Long taskId, Long tagId);

    void hardDelete(Long id);
}