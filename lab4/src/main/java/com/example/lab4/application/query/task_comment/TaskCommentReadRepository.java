package com.example.lab4.application.query.task_comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskCommentReadRepository {

    Page<TaskCommentReadModel> search(Long taskId, Long userId, Pageable pageable);
}
