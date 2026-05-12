package com.example.lab3.unit.application.usecase.tag;

import com.example.lab3.application.usecase.tag.GetTagsUseCase;
import com.example.lab3.domain.model.Tag;
import com.example.lab3.domain.repository.TagRepository;
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
class GetTagsUseCaseTest {

    @Mock
    private TagRepository repository;

    @InjectMocks
    private GetTagsUseCase useCase;

    @Test
    @SuppressWarnings("unchecked")
    void execute_ShouldReturnTagsByColor() {
        String color = "#FF0000";
        Pageable pageable = mock(Pageable.class);
        Page<Tag> expectedPage = mock(Page.class);
        when(repository.search(color, pageable)).thenReturn(expectedPage);

        Page<Tag> result = useCase.execute(color, pageable);

        assertEquals(expectedPage, result);
        verify(repository).search(color, pageable);
    }
}
