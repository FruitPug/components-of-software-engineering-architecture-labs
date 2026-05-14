package com.example.lab3.unit.application.command.task_comment;

import com.example.lab3.application.command.task_comment.CreateTaskCommentCommand;
import com.example.lab3.application.command.task_comment.CreateTaskCommentCommandHandler;
import com.example.lab3.domain.error.DomainError;
import com.example.lab3.domain.model.Task;
import com.example.lab3.domain.model.TaskComment;
import com.example.lab3.domain.model.User;
import com.example.lab3.domain.repository.TaskCommentRepository;
import com.example.lab3.domain.repository.TaskRepository;
import com.example.lab3.domain.repository.UserRepository;
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
class CreateTaskCommentCommandHandlerTest {

    @Mock
    private TaskCommentRepository commentRepository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateTaskCommentCommandHandler useCase;

    @Test
    void handle_WhenValid_ShouldCreateComment() {
        CreateTaskCommentCommand cmd = new CreateTaskCommentCommand(1L, 2L, "Great job!");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(mock(Task.class)));
        when(userRepository.findById(2L)).thenReturn(Optional.of(mock(User.class)));
        when(commentRepository.save(any(TaskComment.class))).thenReturn(mock(TaskComment.class));

        useCase.handle(cmd);

        verify(commentRepository).save(any(TaskComment.class));
    }

    @Test
    void handle_WhenTaskNotFound_ShouldThrowException() {
        CreateTaskCommentCommand cmd = new CreateTaskCommentCommand(1L, 2L, "Body");
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.handle(cmd));
        assertEquals("TASK_NOT_FOUND", exception.getMessage());
    }

    @Test
    void handle_WhenUserNotFound_ShouldThrowException() {
        CreateTaskCommentCommand cmd = new CreateTaskCommentCommand(1L, 2L, "Body");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(mock(Task.class)));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.handle(cmd));
        assertEquals("USER_NOT_FOUND", exception.getMessage());
    }
}
