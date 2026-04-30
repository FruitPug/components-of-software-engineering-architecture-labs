package com.example.lab2.unit.application.usecase.task_tag;

import com.example.lab2.application.usecase.task_tag.GetTaskTagsUseCase;
import com.example.lab2.domain.model.TaskTag;
import com.example.lab2.domain.repository.TaskTagRepository;
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
class GetTaskTagsUseCaseTest {

    @Mock
    private TaskTagRepository repository;

    @InjectMocks
    private GetTaskTagsUseCase useCase;

    @Test
    @SuppressWarnings("unchecked")
    void execute_ShouldReturnTaskTags() {
        Long taskId = 1L;
        Long tagId = 2L;
        Pageable pageable = mock(Pageable.class);
        Page<TaskTag> expectedPage = mock(Page.class);
        when(repository.search(taskId, tagId, pageable)).thenReturn(expectedPage);

        Page<TaskTag> result = useCase.execute(taskId, tagId, pageable);

        assertEquals(expectedPage, result);
        verify(repository).search(taskId, tagId, pageable);
    }
}
