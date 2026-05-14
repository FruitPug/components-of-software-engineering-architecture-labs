package com.example.lab3.unit.application.command.project;

import com.example.lab3.application.command.project.CreateProjectCommand;
import com.example.lab3.application.command.project.CreateProjectCommandHandler;
import com.example.lab3.domain.model.Project;
import com.example.lab3.domain.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProjectCommandHandlerTest {

    @Mock
    private ProjectRepository repository;

    @InjectMocks
    private CreateProjectCommandHandler useCase;

    @Test
    void handle_ShouldCreateAndSaveProject() {
        String name = "New Project";
        String description = "Description";
        Project savedProject = mock(Project.class);
        when(savedProject.getId()).thenReturn(1L);
        when(repository.save(any(Project.class))).thenReturn(savedProject);

        CreateProjectCommand cmd = new CreateProjectCommand(name, description);

        useCase.handle(cmd);

        ArgumentCaptor<Project> projectCaptor = ArgumentCaptor.forClass(Project.class);
        verify(repository).save(projectCaptor.capture());
        Project capturedProject = projectCaptor.getValue();
        assertEquals(name, capturedProject.getName());
        assertEquals(description, capturedProject.getDescription());
    }
}
