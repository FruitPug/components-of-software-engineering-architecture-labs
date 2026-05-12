package com.example.lab3.unit.application.usecase.task_tag;

import com.example.lab3.application.command.task_tag.CreateTaskTagCommand;
import com.example.lab3.application.usecase.task_tag.CreateTaskTagUseCase;
import com.example.lab3.domain.error.DomainError;
import com.example.lab3.domain.model.Tag;
import com.example.lab3.domain.model.Task;
import com.example.lab3.domain.model.TaskTag;
import com.example.lab3.domain.repository.TagRepository;
import com.example.lab3.domain.repository.TaskRepository;
import com.example.lab3.domain.repository.TaskTagRepository;
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
class CreateTaskTagUseCaseTest {

    @Mock
    private TaskTagRepository taskTagRepository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private CreateTaskTagUseCase useCase;

    @Test
    void execute_WhenValid_ShouldCreateTaskTag() {
        CreateTaskTagCommand cmd = new CreateTaskTagCommand(1L, 2L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(mock(Task.class)));
        when(tagRepository.findById(2L)).thenReturn(Optional.of(mock(Tag.class)));
        when(taskTagRepository.exists(1L, 2L)).thenReturn(false);
        when(taskTagRepository.save(any(TaskTag.class))).thenReturn(mock(TaskTag.class));

        useCase.execute(cmd);

        verify(taskTagRepository).save(any(TaskTag.class));
    }

    @Test
    void execute_WhenTaskNotFound_ShouldThrowException() {
        CreateTaskTagCommand cmd = new CreateTaskTagCommand(1L, 2L);
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.execute(cmd));
        assertEquals("TASK_NOT_FOUND", exception.getMessage());
    }

    @Test
    void execute_WhenTagNotFound_ShouldThrowException() {
        CreateTaskTagCommand cmd = new CreateTaskTagCommand(1L, 2L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(mock(Task.class)));
        when(tagRepository.findById(2L)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.execute(cmd));
        assertEquals("TAG_NOT_FOUND", exception.getMessage());
    }

    @Test
    void execute_WhenTaskTagAlreadyExists_ShouldThrowException() {
        CreateTaskTagCommand cmd = new CreateTaskTagCommand(1L, 2L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(mock(Task.class)));
        when(tagRepository.findById(2L)).thenReturn(Optional.of(mock(Tag.class)));
        when(taskTagRepository.exists(1L, 2L)).thenReturn(true);

        DomainError exception = assertThrows(DomainError.class, () -> useCase.execute(cmd));
        assertEquals("TASK_TAG_ALREADY_EXISTS", exception.getMessage());
    }
}
