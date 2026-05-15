package com.example.lab4.unit.application.command.project;

import com.example.lab4.application.command.project.SoftDeleteProjectCommand;
import com.example.lab4.application.command.project.SoftDeleteProjectCommandHandler;
import com.example.lab4.domain.error.DomainError;
import com.example.lab4.domain.model.Project;
import com.example.lab4.domain.repository.ProjectRepository;
import com.example.lab4.domain.repository.TaskRepository;
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
class SoftDeleteProjectCommandHandlerTest {

    @Mock
    private ProjectRepository repository;
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private SoftDeleteProjectCommandHandler useCase;

    @Test
    void handle_WhenProjectExists_ShouldSoftDeleteProjectAndTasks() {
        Long projectId = 1L;
        Project project = mock(Project.class);
        when(repository.findById(projectId)).thenReturn(Optional.of(project));

        SoftDeleteProjectCommand cmd = new SoftDeleteProjectCommand(projectId);

        useCase.handle(cmd);

        verify(project).softDelete();
        verify(repository).save(project);
        verify(taskRepository).softDeleteByProjectId(projectId);
    }

    @Test
    void handle_WhenProjectDoesNotExist_ShouldThrowException() {
        Long projectId = 1L;
        when(repository.findById(projectId)).thenReturn(Optional.empty());

        SoftDeleteProjectCommand cmd = new SoftDeleteProjectCommand(projectId);

        DomainError exception = assertThrows(DomainError.class, () -> useCase.handle(cmd));
        assertEquals("PROJECT_NOT_FOUND", exception.getMessage());
        verify(repository, never()).save(any());
        verify(taskRepository, never()).softDeleteByProjectId(anyLong());
    }
}
