package com.example.lab3.unit.application.command.project;

import com.example.lab3.application.command.project.HardDeleteProjectCommand;
import com.example.lab3.application.command.project.HardDeleteProjectCommandHandler;
import com.example.lab3.domain.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HardDeleteProjectCommandHandlerTest {

    @Mock
    private ProjectRepository repository;

    @InjectMocks
    private HardDeleteProjectCommandHandler useCase;

    @Test
    void handle_ShouldHardDelete() {
        Long projectId = 1L;

        HardDeleteProjectCommand cmd = new HardDeleteProjectCommand(projectId);

        useCase.handle(cmd);

        verify(repository).hardDelete(projectId);
    }
}
