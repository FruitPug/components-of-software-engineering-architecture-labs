package com.example.lab3.unit.application.usecase.project;

import com.example.lab3.application.usecase.project.HardDeleteProjectUseCase;
import com.example.lab3.domain.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HardDeleteProjectUseCaseTest {

    @Mock
    private ProjectRepository repository;

    @InjectMocks
    private HardDeleteProjectUseCase useCase;

    @Test
    void execute_ShouldHardDelete() {
        Long projectId = 1L;

        useCase.execute(projectId);

        verify(repository).hardDelete(projectId);
    }
}
