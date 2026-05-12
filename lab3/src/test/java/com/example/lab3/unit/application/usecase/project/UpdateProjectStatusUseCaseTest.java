package com.example.lab3.unit.application.usecase.project;

import com.example.lab3.application.usecase.project.UpdateProjectStatusUseCase;
import com.example.lab3.domain.enums.ProjectStatus;
import com.example.lab3.domain.error.DomainError;
import com.example.lab3.domain.model.Project;
import com.example.lab3.domain.repository.ProjectRepository;
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
class UpdateProjectStatusUseCaseTest {

    @Mock
    private ProjectRepository repository;

    @InjectMocks
    private UpdateProjectStatusUseCase useCase;

    @Test
    void execute_WhenProjectExists_ShouldUpdateStatusAndSave() {
        Long projectId = 1L;
        ProjectStatus newStatus = ProjectStatus.ARCHIVED;
        Project project = mock(Project.class);
        when(repository.findById(projectId)).thenReturn(Optional.of(project));

        useCase.execute(projectId, newStatus);

        verify(project).updateStatus(newStatus);
        verify(repository).save(project);
    }

    @Test
    void execute_WhenProjectDoesNotExist_ShouldThrowException() {
        Long projectId = 1L;
        ProjectStatus newStatus = ProjectStatus.ARCHIVED;
        when(repository.findById(projectId)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.execute(projectId, newStatus));
        assertEquals("PROJECT_NOT_FOUND", exception.getMessage());
        verify(repository, never()).save(any());
    }
}
