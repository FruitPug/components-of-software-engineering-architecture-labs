package com.example.lab4.unit.application.query.task;

import com.example.lab4.application.query.task.GetTasksQuery;
import com.example.lab4.application.query.task.GetTasksQueryHandler;
import com.example.lab4.application.query.task.TaskReadModel;
import com.example.lab4.application.query.task.TaskReadRepository;
import com.example.lab4.domain.enums.TaskPriority;
import com.example.lab4.domain.enums.TaskStatus;
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
class GetTasksQueryHandlerTest {

    @Mock
    private TaskReadRepository repository;

    @InjectMocks
    private GetTasksQueryHandler useCase;

    @Test
    @SuppressWarnings("unchecked")
    void handle_ShouldReturnTasks() {
        TaskStatus status = TaskStatus.TODO;
        TaskPriority priority = TaskPriority.MEDIUM;
        Long projectId = 1L;
        Long assigneeId = 2L;
        Pageable pageable = mock(Pageable.class);
        Page<TaskReadModel> expectedPage = mock(Page.class);
        when(repository.search(status, priority, projectId, assigneeId, pageable)).thenReturn(expectedPage);

        Page<TaskReadModel> result = useCase.handle(new GetTasksQuery(status, priority, projectId, assigneeId, pageable));

        assertEquals(expectedPage, result);
        verify(repository).search(status, priority, projectId, assigneeId, pageable);
    }
}
