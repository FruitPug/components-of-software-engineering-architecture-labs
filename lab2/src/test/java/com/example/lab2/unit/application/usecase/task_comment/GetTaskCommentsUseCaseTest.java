package com.example.lab2.unit.application.usecase.task_comment;

import com.example.lab2.application.usecase.task_comment.GetTaskCommentsUseCase;
import com.example.lab2.domain.model.TaskComment;
import com.example.lab2.domain.repository.TaskCommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetTaskCommentsUseCaseTest {

    @Mock
    private TaskCommentRepository repository;

    @InjectMocks
    private GetTaskCommentsUseCase useCase;

    @Test
    @SuppressWarnings("unchecked")
    void execute_ShouldReturnComments() {
        Long taskId = 1L;
        Long userId = 2L;
        Pageable pageable = mock(Pageable.class);
        Page<TaskComment> expectedPage = mock(Page.class);
        when(repository.search(taskId, userId, pageable)).thenReturn(expectedPage);

        Page<TaskComment> result = useCase.execute(taskId, userId, pageable);

        assertEquals(expectedPage, result);
        verify(repository).search(taskId, userId, pageable);
    }
}
