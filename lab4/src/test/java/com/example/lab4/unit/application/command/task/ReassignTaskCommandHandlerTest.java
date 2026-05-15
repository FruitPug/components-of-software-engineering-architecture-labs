package com.example.lab4.unit.application.command.task;

import com.example.lab4.application.command.task.ReassignTaskCommand;
import com.example.lab4.application.command.task.ReassignTaskCommandHandler;
import com.example.lab4.domain.error.DomainError;
import com.example.lab4.domain.model.Task;
import com.example.lab4.domain.model.User;
import com.example.lab4.domain.repository.ProjectMemberRepository;
import com.example.lab4.domain.repository.TaskRepository;
import com.example.lab4.domain.repository.UserRepository;
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
class ReassignTaskCommandHandlerTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProjectMemberRepository memberRepository;

    @InjectMocks
    private ReassignTaskCommandHandler useCase;

    @Test
    void handle_WhenValid_ShouldReassignTask() {
        Long taskId = 1L;
        Long newAssigneeId = 2L;
        Long projectId = 10L;

        Task task = mock(Task.class);
        when(task.getProjectId()).thenReturn(projectId);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(userRepository.findById(newAssigneeId)).thenReturn(Optional.of(mock(User.class)));
        when(memberRepository.exists(projectId, newAssigneeId)).thenReturn(true);

        useCase.handle(new ReassignTaskCommand(taskId, newAssigneeId));

        verify(task).reassign(newAssigneeId);
        verify(taskRepository).save(task);
    }

    @Test
    void handle_WhenTaskNotFound_ShouldThrowException() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.handle(new ReassignTaskCommand(1L, 2L)));
        assertEquals("TASK_NOT_FOUND", exception.getMessage());
    }

    @Test
    void handle_WhenUserNotFound_ShouldThrowException() {
        Task task = mock(Task.class);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.handle(new ReassignTaskCommand(1L, 2L)));
        assertEquals("USER_NOT_FOUND", exception.getMessage());
    }

    @Test
    void handle_WhenAssigneeNotMember_ShouldThrowException() {
        Long taskId = 1L;
        Long newAssigneeId = 2L;
        Long projectId = 10L;

        Task task = mock(Task.class);
        when(task.getProjectId()).thenReturn(projectId);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(userRepository.findById(newAssigneeId)).thenReturn(Optional.of(mock(User.class)));
        when(memberRepository.exists(projectId, newAssigneeId)).thenReturn(false);

        DomainError exception = assertThrows(DomainError.class, () -> useCase.handle(new ReassignTaskCommand(taskId, newAssigneeId)));
        assertEquals("ASSIGNEE_NOT_PROJECT_MEMBER", exception.getMessage());
    }
}
