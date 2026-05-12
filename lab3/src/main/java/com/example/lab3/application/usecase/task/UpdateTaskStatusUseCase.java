package com.example.lab3.application.usecase.task;

import com.example.lab3.domain.enums.TaskStatus;
import com.example.lab3.domain.error.DomainError;
import com.example.lab3.domain.model.Task;
import com.example.lab3.domain.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateTaskStatusUseCase {

    private final TaskRepository repository;

    @Transactional
    public void execute(Long taskId, TaskStatus status) {
        Task task = repository.findById(taskId)
                .orElseThrow(() -> new DomainError("TASK_NOT_FOUND"));

        task.updateStatus(status);
        repository.save(task);
    }
}
