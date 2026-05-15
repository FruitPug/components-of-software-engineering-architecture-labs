package com.example.lab4.unit.application.query.tag;

import com.example.lab4.application.query.tag.GetTagsQuery;
import com.example.lab4.application.query.tag.GetTagsQueryHandler;
import com.example.lab4.application.query.tag.TagReadModel;
import com.example.lab4.application.query.tag.TagReadRepository;
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
class GetTagsQueryHandlerTest {

    @Mock
    private TagReadRepository repository;

    @InjectMocks
    private GetTagsQueryHandler useCase;

    @Test
    @SuppressWarnings("unchecked")
    void handle_ShouldReturnTagsByColor() {
        String color = "#FF0000";
        Pageable pageable = mock(Pageable.class);
        Page<TagReadModel> expectedPage = mock(Page.class);
        when(repository.search(color, pageable)).thenReturn(expectedPage);

        Page<TagReadModel> result = useCase.handle(new GetTagsQuery(color, pageable));

        assertEquals(expectedPage, result);
        verify(repository).search(color, pageable);
    }
}
