package com.example.lab4.unit.application.command.task;

import com.example.lab4.application.command.task.UpdateTaskStatusCommand;
import com.example.lab4.application.command.task.UpdateTaskStatusCommandHandler;
import com.example.lab4.domain.enums.TaskStatus;
import com.example.lab4.domain.error.DomainError;
import com.example.lab4.domain.model.Task;
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
class UpdateTaskStatusCommandHandlerTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private UpdateTaskStatusCommandHandler useCase;

    @Test
    void handle_WhenTaskExists_ShouldUpdateStatusAndSave() {
        Long taskId = 1L;
        TaskStatus newStatus = TaskStatus.IN_PROGRESS;
        Task task = mock(Task.class);
        when(repository.findById(taskId)).thenReturn(Optional.of(task));

        useCase.handle(new UpdateTaskStatusCommand(taskId, newStatus));

        verify(task).updateStatus(newStatus);
        verify(repository).save(task);
    }

    @Test
    void handle_WhenTaskDoesNotExist_ShouldThrowException() {
        Long taskId = 1L;
        TaskStatus newStatus = TaskStatus.IN_PROGRESS;
        when(repository.findById(taskId)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.handle(new UpdateTaskStatusCommand(taskId, newStatus)));
        assertEquals("TASK_NOT_FOUND", exception.getMessage());
        verify(repository, never()).save(any());
    }
}
