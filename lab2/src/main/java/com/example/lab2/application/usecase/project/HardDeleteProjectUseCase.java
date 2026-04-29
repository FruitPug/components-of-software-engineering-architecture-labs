package com.example.lab2.application.usecase.project;

import com.example.lab2.domain.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HardDeleteProjectUseCase {

    private final ProjectRepository repository;

    public void execute(Long id) {
        repository.hardDelete(id);
    }
}
