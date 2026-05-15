package com.example.lab4.unit.application.query.task_comment;

import com.example.lab4.application.query.task_comment.GetTaskCommentsQuery;
import com.example.lab4.application.query.task_comment.GetTaskCommentsQueryHandler;
import com.example.lab4.application.query.task_comment.TaskCommentReadModel;
import com.example.lab4.application.query.task_comment.TaskCommentReadRepository;
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
class GetTaskCommentsQueryHandlerTest {

    @Mock
    private TaskCommentReadRepository repository;

    @InjectMocks
    private GetTaskCommentsQueryHandler useCase;

    @Test
    @SuppressWarnings("unchecked")
    void handle_ShouldReturnComments() {
        Long taskId = 1L;
        Long userId = 2L;
        Pageable pageable = mock(Pageable.class);
        Page<TaskCommentReadModel> expectedPage = mock(Page.class);
        when(repository.search(taskId, userId, pageable)).thenReturn(expectedPage);

        Page<TaskCommentReadModel> result = useCase.handle(new GetTaskCommentsQuery(taskId, userId, pageable));

        assertEquals(expectedPage, result);
        verify(repository).search(taskId, userId, pageable);
    }
}
