package com.example.lab4.domain.repository;

import com.example.lab4.domain.model.TaskTag;

public interface TaskTagRepository {

    TaskTag save(TaskTag taskTag);

    boolean exists(Long taskId, Long tagId);

    void hardDelete(Long id);
}