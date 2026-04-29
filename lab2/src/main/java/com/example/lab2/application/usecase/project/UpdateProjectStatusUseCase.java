package com.example.lab2.application.usecase.project;

import com.example.lab2.domain.enums.ProjectStatus;
import com.example.lab2.domain.error.DomainError;
import com.example.lab2.domain.model.Project;
import com.example.lab2.domain.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProjectStatusUseCase {

    private final ProjectRepository repository;

    public void execute(Long projectId, ProjectStatus status) {
        Project project = repository.findById(projectId)
                .orElseThrow(() -> new DomainError("PROJECT_NOT_FOUND"));

        project.updateStatus(status);
        repository.save(project);
    }
}
