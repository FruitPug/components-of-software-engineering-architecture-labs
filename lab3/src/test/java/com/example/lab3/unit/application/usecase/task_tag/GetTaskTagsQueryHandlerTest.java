package com.example.lab3.unit.application.usecase.task_tag;

import com.example.lab3.application.query.task_tag.GetTaskTagsQuery;
import com.example.lab3.application.query.task_tag.GetTaskTagsQueryHandler;
import com.example.lab3.application.query.task_tag.TaskTagReadModel;
import com.example.lab3.application.query.task_tag.TaskTagReadRepository;
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
class GetTaskTagsQueryHandlerTest {

    @Mock
    private TaskTagReadRepository repository;

    @InjectMocks
    private GetTaskTagsQueryHandler useCase;

    @Test
    @SuppressWarnings("unchecked")
    void handle_ShouldReturnTaskTags() {
        Long taskId = 1L;
        Long tagId = 2L;
        Pageable pageable = mock(Pageable.class);
        Page<TaskTagReadModel> expectedPage = mock(Page.class);
        when(repository.search(taskId, tagId, pageable)).thenReturn(expectedPage);

        Page<TaskTagReadModel> result = useCase.handle(new GetTaskTagsQuery(taskId, tagId, pageable));

        assertEquals(expectedPage, result);
        verify(repository).search(taskId, tagId, pageable);
    }
}
