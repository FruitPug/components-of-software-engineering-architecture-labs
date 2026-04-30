package com.example.lab2.unit.application.usecase.project;

import com.example.lab2.application.usecase.project.GetProjectsUseCase;
import com.example.lab2.domain.enums.ProjectStatus;
import com.example.lab2.domain.model.Project;
import com.example.lab2.domain.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetProjectsUseCaseTest {

    @Mock
    private ProjectRepository repository;

    @InjectMocks
    private GetProjectsUseCase useCase;

    @Test
    @SuppressWarnings("unchecked")
    void execute_ShouldReturnProjectsByStatus() {
        ProjectStatus status = ProjectStatus.ACTIVE;
        Pageable pageable = mock(Pageable.class);
        Page<Project> expectedPage = mock(Page.class);
        when(repository.search(status, pageable)).thenReturn(expectedPage);

        Page<Project> result = useCase.execute(status, pageable);

        assertEquals(expectedPage, result);
        verify(repository).search(status, pageable);
    }
}
