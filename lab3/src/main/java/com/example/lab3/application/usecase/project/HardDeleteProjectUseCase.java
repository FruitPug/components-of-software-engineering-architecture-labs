package com.example.lab3.application.usecase.project;

import com.example.lab3.domain.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HardDeleteProjectUseCase {

    private final ProjectRepository repository;

    @Transactional
    public void execute(Long id) {
        repository.hardDelete(id);
    }
}
