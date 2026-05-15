package com.example.lab4.application.command.project;

import com.example.lab4.domain.error.DomainError;
import com.example.lab4.domain.model.Project;
import com.example.lab4.domain.repository.ProjectRepository;
import com.example.lab4.domain.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SoftDeleteProjectCommandHandler {

    private final ProjectRepository repository;
    private final TaskRepository taskRepository;

    @Transactional
    public void handle(SoftDeleteProjectCommand cmd) {
        Project project = repository.findById(cmd.projectId())
                .orElseThrow(() -> new DomainError("PROJECT_NOT_FOUND"));

        project.softDelete();
        repository.save(project);

        taskRepository.softDeleteByProjectId(cmd.projectId());
    }
}
