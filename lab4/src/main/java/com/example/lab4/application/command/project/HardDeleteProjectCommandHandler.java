package com.example.lab4.application.command.project;

import com.example.lab4.domain.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HardDeleteProjectCommandHandler {

    private final ProjectRepository repository;

    @Transactional
    public void handle(HardDeleteProjectCommand cmd) {
        repository.hardDelete(cmd.projectId());
    }
}
