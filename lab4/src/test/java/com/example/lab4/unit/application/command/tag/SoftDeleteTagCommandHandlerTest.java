package com.example.lab4.unit.application.command.tag;

import com.example.lab4.application.command.tag.SoftDeleteTagCommand;
import com.example.lab4.application.command.tag.SoftDeleteTagCommandHandler;
import com.example.lab4.domain.error.DomainError;
import com.example.lab4.domain.model.Tag;
import com.example.lab4.domain.repository.TagRepository;
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
class SoftDeleteTagCommandHandlerTest {

    @Mock
    private TagRepository repository;

    @InjectMocks
    private SoftDeleteTagCommandHandler useCase;

    @Test
    void handle_WhenTagExists_ShouldSoftDeleteAndSave() {
        Long tagId = 1L;
        Tag tag = mock(Tag.class);
        when(repository.findById(tagId)).thenReturn(Optional.of(tag));

        useCase.handle(new SoftDeleteTagCommand(tagId));

        verify(tag).softDelete();
        verify(repository).save(tag);
    }

    @Test
    void handle_WhenTagDoesNotExist_ShouldThrowException() {
        Long tagId = 1L;
        when(repository.findById(tagId)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.handle(new SoftDeleteTagCommand(tagId)));
        assertEquals("TAG_NOT_FOUND", exception.getMessage());
        verify(repository, never()).save(any());
    }
}
