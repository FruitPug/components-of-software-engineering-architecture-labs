package com.example.lab2.unit.application.usecase.project;

import com.example.lab2.application.usecase.project.SoftDeleteProjectUseCase;
import com.example.lab2.domain.error.DomainError;
import com.example.lab2.domain.model.Project;
import com.example.lab2.domain.repository.ProjectRepository;
import com.example.lab2.domain.repository.TaskRepository;
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
class SoftDeleteProjectUseCaseTest {

    @Mock
    private ProjectRepository repository;
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private SoftDeleteProjectUseCase useCase;

    @Test
    void execute_WhenProjectExists_ShouldSoftDeleteProjectAndTasks() {
        Long projectId = 1L;
        Project project = mock(Project.class);
        when(repository.findById(projectId)).thenReturn(Optional.of(project));

        useCase.execute(projectId);

        verify(project).softDelete();
        verify(repository).save(project);
        verify(taskRepository).softDeleteByProjectId(projectId);
    }

    @Test
    void execute_WhenProjectDoesNotExist_ShouldThrowException() {
        Long projectId = 1L;
        when(repository.findById(projectId)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.execute(projectId));
        assertEquals("PROJECT_NOT_FOUND", exception.getMessage());
        verify(repository, never()).save(any());
        verify(taskRepository, never()).softDeleteByProjectId(anyLong());
    }
}
