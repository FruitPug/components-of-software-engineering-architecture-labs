package com.example.lab3.application.usecase.project;

import com.example.lab3.domain.error.DomainError;
import com.example.lab3.domain.model.Project;
import com.example.lab3.domain.repository.ProjectRepository;
import com.example.lab3.domain.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SoftDeleteProjectUseCase {

    private final ProjectRepository repository;
    private final TaskRepository taskRepository;

    @Transactional
    public void execute(Long id) {
        Project project = repository.findById(id)
                .orElseThrow(() -> new DomainError("PROJECT_NOT_FOUND"));

        project.softDelete();
        repository.save(project);

        taskRepository.softDeleteByProjectId(id);
    }
}
