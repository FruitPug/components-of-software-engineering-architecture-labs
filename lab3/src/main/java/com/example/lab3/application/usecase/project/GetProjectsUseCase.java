package com.example.lab3.application.usecase.project;

import com.example.lab3.domain.enums.ProjectStatus;
import com.example.lab3.domain.model.Project;
import com.example.lab3.domain.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetProjectsUseCase {

    private final ProjectRepository repository;

    @Transactional
    public Page<Project> execute(ProjectStatus status, Pageable pageable) {
        return repository.search(status, pageable);
    }
}
