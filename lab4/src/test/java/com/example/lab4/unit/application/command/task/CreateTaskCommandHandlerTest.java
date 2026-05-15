package com.example.lab4.unit.application.command.task;

import com.example.lab4.application.command.task.CreateTaskCommand;
import com.example.lab4.application.command.task.CreateTaskCommandHandler;
import com.example.lab4.domain.enums.TaskPriority;
import com.example.lab4.domain.error.DomainError;
import com.example.lab4.domain.model.Project;
import com.example.lab4.domain.model.Task;
import com.example.lab4.domain.model.User;
import com.example.lab4.domain.repository.ProjectRepository;
import com.example.lab4.domain.repository.TaskRepository;
import com.example.lab4.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTaskCommandHandlerTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateTaskCommandHandler useCase;

    @Test
    void handle_WhenValid_ShouldCreateTask() {
        CreateTaskCommand cmd = new CreateTaskCommand(1L, 2L, 3L, "Task 1", "Desc", TaskPriority.HIGH, LocalDate.now());
        when(projectRepository.findById(1L)).thenReturn(Optional.of(mock(Project.class)));
        when(userRepository.findById(2L)).thenReturn(Optional.of(mock(User.class)));
        when(userRepository.findById(3L)).thenReturn(Optional.of(mock(User.class)));
        when(taskRepository.save(any(Task.class))).thenReturn(mock(Task.class));

        useCase.handle(cmd);

        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void handle_WhenProjectNotFound_ShouldThrowException() {
        CreateTaskCommand cmd = new CreateTaskCommand(1L, 2L, 3L, "T1", "D", TaskPriority.LOW, LocalDate.now());
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.handle(cmd));
        assertEquals("PROJECT_NOT_FOUND", exception.getMessage());
    }

    @Test
    void handle_WhenCreatorNotFound_ShouldThrowException() {
        CreateTaskCommand cmd = new CreateTaskCommand(1L, 2L, 3L, "T1", "D", TaskPriority.LOW, LocalDate.now());
        when(projectRepository.findById(1L)).thenReturn(Optional.of(mock(Project.class)));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.handle(cmd));
        assertEquals("CREATOR_NOT_FOUND", exception.getMessage());
    }

    @Test
    void handle_WhenAssigneeNotFound_ShouldThrowException() {
        CreateTaskCommand cmd = new CreateTaskCommand(1L, 2L, 3L, "T1", "D", TaskPriority.LOW, LocalDate.now());
        when(projectRepository.findById(1L)).thenReturn(Optional.of(mock(Project.class)));
        when(userRepository.findById(2L)).thenReturn(Optional.of(mock(User.class)));
        when(userRepository.findById(3L)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.handle(cmd));
        assertEquals("ASSIGNEE_NOT_FOUND", exception.getMessage());
    }
}
