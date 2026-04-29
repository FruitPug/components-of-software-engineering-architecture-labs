package com.example.lab2.application.usecase.project;

import com.example.lab2.domain.error.DomainError;
import com.example.lab2.domain.model.Project;
import com.example.lab2.domain.repository.ProjectRepository;
import com.example.lab2.domain.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SoftDeleteProjectUseCase {

    private final ProjectRepository repository;
    private final TaskRepository taskRepository;

    public void execute(Long id) {
        Project project = repository.findById(id)
                .orElseThrow(() -> new DomainError("PROJECT_NOT_FOUND"));

        project.softDelete();
        repository.save(project);

        taskRepository.softDeleteByProjectId(id);
    }
}
