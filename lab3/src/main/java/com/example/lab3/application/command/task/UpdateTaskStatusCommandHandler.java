package com.example.lab3.application.command.task;

import com.example.lab3.domain.error.DomainError;
import com.example.lab3.domain.model.Task;
import com.example.lab3.domain.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateTaskStatusCommandHandler {

    private final TaskRepository repository;

    @Transactional
    public void handle(UpdateTaskStatusCommand cmd) {
        Task task = repository.findById(cmd.taskId())
                .orElseThrow(() -> new DomainError("TASK_NOT_FOUND"));

        task.updateStatus(cmd.status());
        repository.save(task);
    }
}
