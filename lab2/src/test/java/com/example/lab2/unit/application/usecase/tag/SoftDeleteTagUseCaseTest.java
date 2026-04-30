package com.example.lab2.unit.application.usecase.tag;

import com.example.lab2.application.usecase.tag.SoftDeleteTagUseCase;
import com.example.lab2.domain.error.DomainError;
import com.example.lab2.domain.model.Tag;
import com.example.lab2.domain.repository.TagRepository;
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
class SoftDeleteTagUseCaseTest {

    @Mock
    private TagRepository repository;

    @InjectMocks
    private SoftDeleteTagUseCase useCase;

    @Test
    void execute_WhenTagExists_ShouldSoftDeleteAndSave() {
        Long tagId = 1L;
        Tag tag = mock(Tag.class);
        when(repository.findById(tagId)).thenReturn(Optional.of(tag));

        useCase.execute(tagId);

        verify(tag).softDelete();
        verify(repository).save(tag);
    }

    @Test
    void execute_WhenTagDoesNotExist_ShouldThrowException() {
        Long tagId = 1L;
        when(repository.findById(tagId)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.execute(tagId));
        assertEquals("TAG_NOT_FOUND", exception.getMessage());
        verify(repository, never()).save(any());
    }
}
