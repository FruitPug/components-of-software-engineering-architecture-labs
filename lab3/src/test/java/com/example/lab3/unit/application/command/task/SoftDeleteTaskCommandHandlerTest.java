package com.example.lab3.unit.application.command.task;

import com.example.lab3.application.command.task.SoftDeleteTaskCommand;
import com.example.lab3.application.command.task.SoftDeleteTaskCommandHandler;
import com.example.lab3.domain.error.DomainError;
import com.example.lab3.domain.model.Task;
import com.example.lab3.domain.repository.TaskRepository;
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
class SoftDeleteTaskCommandHandlerTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private SoftDeleteTaskCommandHandler useCase;

    @Test
    void handle_WhenTaskExists_ShouldSoftDeleteAndSave() {
        Long taskId = 1L;
        Task task = mock(Task.class);
        when(repository.findById(taskId)).thenReturn(Optional.of(task));

        useCase.handle(new SoftDeleteTaskCommand(taskId));

        verify(task).softDelete();
        verify(repository).save(task);
    }

    @Test
    void handle_WhenTaskDoesNotExist_ShouldThrowException() {
        Long taskId = 1L;
        when(repository.findById(taskId)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.handle(new SoftDeleteTaskCommand(taskId)));
        assertEquals("TASK_NOT_FOUND", exception.getMessage());
        verify(repository, never()).save(any());
    }
}
