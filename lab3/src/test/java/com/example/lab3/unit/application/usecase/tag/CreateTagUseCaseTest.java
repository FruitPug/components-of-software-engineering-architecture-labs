package com.example.lab3.unit.application.usecase.tag;

import com.example.lab3.application.usecase.tag.CreateTagUseCase;
import com.example.lab3.domain.model.Tag;
import com.example.lab3.domain.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTagUseCaseTest {

    @Mock
    private TagRepository repository;

    @InjectMocks
    private CreateTagUseCase useCase;

    @Test
    void execute_ShouldCreateAndSaveTag() {
        String name = "Urgent";
        String color = "#FF0000";
        Tag savedTag = mock(Tag.class);
        when(savedTag.getId()).thenReturn(1L);
        when(repository.save(any(Tag.class))).thenReturn(savedTag);

        useCase.execute(name, color);

        ArgumentCaptor<Tag> tagCaptor = ArgumentCaptor.forClass(Tag.class);
        verify(repository).save(tagCaptor.capture());
        Tag capturedTag = tagCaptor.getValue();
        assertEquals(name, capturedTag.getName());
        assertEquals(color, capturedTag.getColor());
    }
}
