package com.example.lab3.unit.application.usecase.task;

import com.example.lab3.application.usecase.task.GetTasksUseCase;
import com.example.lab3.domain.enums.TaskPriority;
import com.example.lab3.domain.enums.TaskStatus;
import com.example.lab3.domain.model.Task;
import com.example.lab3.domain.repository.TaskRepository;
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
class GetTasksUseCaseTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private GetTasksUseCase useCase;

    @Test
    @SuppressWarnings("unchecked")
    void execute_ShouldReturnTasks() {
        TaskStatus status = TaskStatus.TODO;
        TaskPriority priority = TaskPriority.MEDIUM;
        Long projectId = 1L;
        Long assigneeId = 2L;
        Pageable pageable = mock(Pageable.class);
        Page<Task> expectedPage = mock(Page.class);
        when(repository.search(status, priority, projectId, assigneeId, pageable)).thenReturn(expectedPage);

        Page<Task> result = useCase.execute(status, priority, projectId, assigneeId, pageable);

        assertEquals(expectedPage, result);
        verify(repository).search(status, priority, projectId, assigneeId, pageable);
    }
}
