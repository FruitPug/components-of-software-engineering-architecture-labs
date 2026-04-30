package com.example.lab2.application.usecase.task;

import com.example.lab2.domain.error.DomainError;
import com.example.lab2.domain.model.Task;
import com.example.lab2.domain.repository.ProjectRepository;
import com.example.lab2.domain.repository.TaskRepository;
import com.example.lab2.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTaskUseCase {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Transactional
    public void execute(CreateTaskCommand cmd) {

        projectRepository.findById(cmd.projectId())
                .orElseThrow(() -> new DomainError("PROJECT_NOT_FOUND"));

        userRepository.findById(cmd.creatorId())
                .orElseThrow(() -> new DomainError("CREATOR_NOT_FOUND"));

        if (cmd.assigneeId() != null) {
            userRepository.findById(cmd.assigneeId())
                    .orElseThrow(() -> new DomainError("ASSIGNEE_NOT_FOUND"));
        }

        Task task = new Task(
                cmd.projectId(),
                cmd.creatorId(),
                cmd.assigneeId(),
                cmd.title(),
                cmd.description(),
                cmd.priority(),
                cmd.dueDate()
        );

        Task savedTask = taskRepository.save(task);
        task.setId(savedTask.getId());
    }
}
