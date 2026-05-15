package com.example.lab4.application.query.task;

import com.example.lab4.domain.enums.TaskPriority;
import com.example.lab4.domain.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskReadRepository {
    Page<TaskReadModel> search(
            TaskStatus status,
            TaskPriority priority,
            Long projectId,
            Long assigneeId,
            Pageable pageable
    );
}
