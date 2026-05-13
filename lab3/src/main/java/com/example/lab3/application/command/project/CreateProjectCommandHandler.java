package com.example.lab3.application.command.project;

import com.example.lab3.domain.model.Project;
import com.example.lab3.domain.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProjectCommandHandler {

    private final ProjectRepository repository;

    @Transactional
    public void handle(CreateProjectCommand cmd) {
        Project project = new Project(cmd.name(), cmd.description());
        Project savedProject = repository.save(project);
        project.setId(savedProject.getId());
    }
}
