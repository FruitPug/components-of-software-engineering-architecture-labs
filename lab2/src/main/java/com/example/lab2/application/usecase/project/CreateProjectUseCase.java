package com.example.lab2.application.usecase.project;

import com.example.lab2.domain.model.Project;
import com.example.lab2.domain.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProjectUseCase {

    private final ProjectRepository repository;

    @Transactional
    public void execute(String name, String description) {
        Project project = new Project(name, description);
        Project savedProject = repository.save(project);
        project.setId(savedProject.getId());
    }
}
