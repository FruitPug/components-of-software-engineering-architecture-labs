package com.example.lab2.unit.application.usecase.project;

import com.example.lab2.application.usecase.project.CreateProjectUseCase;
import com.example.lab2.domain.model.Project;
import com.example.lab2.domain.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProjectUseCaseTest {

    @Mock
    private ProjectRepository repository;

    @InjectMocks
    private CreateProjectUseCase useCase;

    @Test
    void execute_ShouldCreateAndSaveProject() {
        String name = "New Project";
        String description = "Description";
        Project savedProject = mock(Project.class);
        when(savedProject.getId()).thenReturn(1L);
        when(repository.save(any(Project.class))).thenReturn(savedProject);

        useCase.execute(name, description);

        ArgumentCaptor<Project> projectCaptor = ArgumentCaptor.forClass(Project.class);
        verify(repository).save(projectCaptor.capture());
        Project capturedProject = projectCaptor.getValue();
        assertEquals(name, capturedProject.getName());
        assertEquals(description, capturedProject.getDescription());
    }
}
