package com.example.lab4.unit.application.command.task_tag;

import com.example.lab4.application.command.task_tag.CreateTaskTagCommand;
import com.example.lab4.application.command.task_tag.CreateTaskTagCommandHandler;
import com.example.lab4.domain.error.DomainError;
import com.example.lab4.domain.model.Tag;
import com.example.lab4.domain.model.Task;
import com.example.lab4.domain.model.TaskTag;
import com.example.lab4.domain.repository.TagRepository;
import com.example.lab4.domain.repository.TaskRepository;
import com.example.lab4.domain.repository.TaskTagRepository;
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
class CreateTaskTagCommandHandlerTest {

    @Mock
    private TaskTagRepository taskTagRepository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private CreateTaskTagCommandHandler useCase;

    @Test
    void handle_WhenValid_ShouldCreateTaskTag() {
        CreateTaskTagCommand cmd = new CreateTaskTagCommand(1L, 2L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(mock(Task.class)));
        when(tagRepository.findById(2L)).thenReturn(Optional.of(mock(Tag.class)));
        when(taskTagRepository.exists(1L, 2L)).thenReturn(false);
        when(taskTagRepository.save(any(TaskTag.class))).thenReturn(mock(TaskTag.class));

        useCase.handle(cmd);

        verify(taskTagRepository).save(any(TaskTag.class));
    }

    @Test
    void handle_WhenTaskNotFound_ShouldThrowException() {
        CreateTaskTagCommand cmd = new CreateTaskTagCommand(1L, 2L);
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.handle(cmd));
        assertEquals("TASK_NOT_FOUND", exception.getMessage());
    }

    @Test
    void handle_WhenTagNotFound_ShouldThrowException() {
        CreateTaskTagCommand cmd = new CreateTaskTagCommand(1L, 2L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(mock(Task.class)));
        when(tagRepository.findById(2L)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.handle(cmd));
        assertEquals("TAG_NOT_FOUND", exception.getMessage());
    }

    @Test
    void handle_WhenTaskTagAlreadyExists_ShouldThrowException() {
        CreateTaskTagCommand cmd = new CreateTaskTagCommand(1L, 2L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(mock(Task.class)));
        when(tagRepository.findById(2L)).thenReturn(Optional.of(mock(Tag.class)));
        when(taskTagRepository.exists(1L, 2L)).thenReturn(true);

        DomainError exception = assertThrows(DomainError.class, () -> useCase.handle(cmd));
        assertEquals("TASK_TAG_ALREADY_EXISTS", exception.getMessage());
    }
}
