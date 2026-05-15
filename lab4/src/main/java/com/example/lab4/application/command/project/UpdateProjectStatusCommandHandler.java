package com.example.lab4.application.command.project;

import com.example.lab4.domain.error.DomainError;
import com.example.lab4.domain.model.Project;
import com.example.lab4.domain.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProjectStatusCommandHandler {

    private final ProjectRepository repository;

    @Transactional
    public void handle(UpdateProjectStatusCommand cmd) {
        Project project = repository.findById(cmd.projectId())
                .orElseThrow(() -> new DomainError("PROJECT_NOT_FOUND"));

        project.updateStatus(cmd.status());
        repository.save(project);
    }
}
