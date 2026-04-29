package com.example.lab2.application.usecase.task;

import com.example.lab2.domain.error.DomainError;
import com.example.lab2.domain.model.Task;
import com.example.lab2.domain.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SoftDeleteTaskUseCase {

    private final TaskRepository repository;

    public void execute(Long id) {

        Task task = repository.findById(id)
                .orElseThrow(() -> new DomainError("TASK_NOT_FOUND"));

        task.softDelete();
        repository.save(task);
    }
}
