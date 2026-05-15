package com.example.lab4.unit.application.command.task_comment;

import com.example.lab4.application.command.task_comment.SoftDeleteTaskCommentCommand;
import com.example.lab4.application.command.task_comment.SoftDeleteTaskCommentCommandHandler;
import com.example.lab4.domain.error.DomainError;
import com.example.lab4.domain.model.TaskComment;
import com.example.lab4.domain.repository.TaskCommentRepository;
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
class SoftDeleteTaskCommentCommandHandlerTest {

    @Mock
    private TaskCommentRepository repository;

    @InjectMocks
    private SoftDeleteTaskCommentCommandHandler useCase;

    @Test
    void handle_WhenCommentExists_ShouldSoftDeleteAndSave() {
        Long commentId = 1L;
        TaskComment comment = mock(TaskComment.class);
        when(repository.findById(commentId)).thenReturn(Optional.of(comment));

        useCase.handle(new SoftDeleteTaskCommentCommand(commentId));

        verify(comment).softDelete();
        verify(repository).save(comment);
    }

    @Test
    void handle_WhenCommentDoesNotExist_ShouldThrowException() {
        Long commentId = 1L;
        when(repository.findById(commentId)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.handle(new SoftDeleteTaskCommentCommand(commentId)));
        assertEquals("COMMENT_NOT_FOUND", exception.getMessage());
        verify(repository, never()).save(any());
    }
}
