package com.example.lab2.domain.repository;

import com.example.lab2.domain.enums.TaskPriority;
import com.example.lab2.domain.enums.TaskStatus;
import com.example.lab2.domain.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);

    Optional<Task> findById(Long id);

    Page<Task> search(
            TaskStatus status,
            TaskPriority priority,
            Long projectId,
            Long assigneeId,
            Pageable pageable
    );

    void softDeleteByProjectId(Long projectId);
}
