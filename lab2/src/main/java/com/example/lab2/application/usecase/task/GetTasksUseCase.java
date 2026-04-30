package com.example.lab2.application.usecase.task;

import com.example.lab2.domain.enums.TaskPriority;
import com.example.lab2.domain.enums.TaskStatus;
import com.example.lab2.domain.model.Task;
import com.example.lab2.domain.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTasksUseCase {

    private final TaskRepository repository;

    @Transactional
    public Page<Task> execute(
            TaskStatus status,
            TaskPriority priority,
            Long projectId,
            Long assigneeId,
            Pageable pageable
    ) {
        return repository.search(status, priority, projectId, assigneeId, pageable);
    }
}
