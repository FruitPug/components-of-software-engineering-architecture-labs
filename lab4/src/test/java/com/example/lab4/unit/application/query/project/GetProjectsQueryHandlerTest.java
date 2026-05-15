package com.example.lab4.unit.application.query.project;

import com.example.lab4.application.query.project.GetProjectsQuery;
import com.example.lab4.application.query.project.GetProjectsQueryHandler;
import com.example.lab4.application.query.project.ProjectReadModel;
import com.example.lab4.application.query.project.ProjectReadRepository;
import com.example.lab4.domain.enums.ProjectStatus;
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
class GetProjectsQueryHandlerTest {

    @Mock
    private ProjectReadRepository repository;

    @InjectMocks
    private GetProjectsQueryHandler useCase;

    @Test
    @SuppressWarnings("unchecked")
    void execute_ShouldReturnProjectsByStatus() {
        ProjectStatus status = ProjectStatus.ACTIVE;
        Pageable pageable = mock(Pageable.class);
        Page<ProjectReadModel> expectedPage = mock(Page.class);

        when(repository.search(status, pageable)).thenReturn(expectedPage);

        GetProjectsQuery query = new GetProjectsQuery(status, pageable);

        Page<ProjectReadModel> result = useCase.handle(query);

        assertEquals(expectedPage, result);
        verify(repository).search(status, pageable);
    }
}
