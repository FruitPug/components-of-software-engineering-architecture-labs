package com.example.lab3.unit.application.usecase.task_comment;

import com.example.lab3.application.usecase.task_comment.SoftDeleteTaskCommentUseCase;
import com.example.lab3.domain.error.DomainError;
import com.example.lab3.domain.model.TaskComment;
import com.example.lab3.domain.repository.TaskCommentRepository;
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
class SoftDeleteTaskCommentUseCaseTest {

    @Mock
    private TaskCommentRepository repository;

    @InjectMocks
    private SoftDeleteTaskCommentUseCase useCase;

    @Test
    void execute_WhenCommentExists_ShouldSoftDeleteAndSave() {
        Long commentId = 1L;
        TaskComment comment = mock(TaskComment.class);
        when(repository.findById(commentId)).thenReturn(Optional.of(comment));

        useCase.execute(commentId);

        verify(comment).softDelete();
        verify(repository).save(comment);
    }

    @Test
    void execute_WhenCommentDoesNotExist_ShouldThrowException() {
        Long commentId = 1L;
        when(repository.findById(commentId)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.execute(commentId));
        assertEquals("COMMENT_NOT_FOUND", exception.getMessage());
        verify(repository, never()).save(any());
    }
}
