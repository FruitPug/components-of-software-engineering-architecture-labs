package com.example.lab4.application.query.task_tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskTagReadRepository {

    Page<TaskTagReadModel> search(Long taskId, Long tagId, Pageable pageable);
}
