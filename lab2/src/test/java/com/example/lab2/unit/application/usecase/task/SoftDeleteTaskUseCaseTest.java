package com.example.lab2.unit.application.usecase.task;

import com.example.lab2.application.usecase.task.SoftDeleteTaskUseCase;
import com.example.lab2.domain.error.DomainError;
import com.example.lab2.domain.model.Task;
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
class SoftDeleteTaskUseCaseTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private SoftDeleteTaskUseCase useCase;

    @Test
    void execute_WhenTaskExists_ShouldSoftDeleteAndSave() {
        Long taskId = 1L;
        Task task = mock(Task.class);
        when(repository.findById(taskId)).thenReturn(Optional.of(task));

        useCase.execute(taskId);

        verify(task).softDelete();
        verify(repository).save(task);
    }

    @Test
    void execute_WhenTaskDoesNotExist_ShouldThrowException() {
        Long taskId = 1L;
        when(repository.findById(taskId)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.execute(taskId));
        assertEquals("TASK_NOT_FOUND", exception.getMessage());
        verify(repository, never()).save(any());
    }
}
