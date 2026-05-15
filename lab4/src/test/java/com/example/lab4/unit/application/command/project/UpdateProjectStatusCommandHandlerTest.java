package com.example.lab4.unit.application.command.project;

import com.example.lab4.application.command.project.UpdateProjectStatusCommand;
import com.example.lab4.application.command.project.UpdateProjectStatusCommandHandler;
import com.example.lab4.domain.enums.ProjectStatus;
import com.example.lab4.domain.error.DomainError;
import com.example.lab4.domain.model.Project;
import com.example.lab4.domain.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateProjectStatusCommandHandlerTest {

    @Mock
    private ProjectRepository repository;

    @InjectMocks
    private UpdateProjectStatusCommandHandler useCase;

    @Test
    void handle_WhenProjectExists_ShouldUpdateStatusAndSave() {
        Long projectId = 1L;
        ProjectStatus newStatus = ProjectStatus.ARCHIVED;
        Project project = mock(Project.class);
        when(repository.findById(projectId)).thenReturn(Optional.of(project));

        UpdateProjectStatusCommand cmd = new UpdateProjectStatusCommand(projectId, newStatus);

        useCase.handle(cmd);

        verify(project).updateStatus(newStatus);
        verify(repository).save(project);
    }

    @Test
    void handle_WhenProjectDoesNotExist_ShouldThrowException() {
        Long projectId = 1L;
        ProjectStatus newStatus = ProjectStatus.ARCHIVED;
        when(repository.findById(projectId)).thenReturn(Optional.empty());

        UpdateProjectStatusCommand cmd = new UpdateProjectStatusCommand(projectId, newStatus);

        DomainError exception = assertThrows(DomainError.class, () -> useCase.handle(cmd));
        assertEquals("PROJECT_NOT_FOUND", exception.getMessage());
        verify(repository, never()).save(any());
    }
}
