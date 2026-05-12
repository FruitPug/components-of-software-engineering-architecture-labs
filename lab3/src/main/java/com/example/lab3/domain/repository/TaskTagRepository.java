package com.example.lab3.domain.repository;

import com.example.lab3.domain.model.TaskTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskTagRepository {

    TaskTag save(TaskTag taskTag);

    boolean exists(Long taskId, Long tagId);

    Page<TaskTag> search(Long taskId, Long tagId, Pageable pageable);

    void hardDelete(Long id);
}