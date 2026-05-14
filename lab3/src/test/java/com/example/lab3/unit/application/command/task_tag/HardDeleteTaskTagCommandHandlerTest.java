package com.example.lab3.unit.application.command.task_tag;

import com.example.lab3.application.command.task_tag.HardDeleteTaskTagCommand;
import com.example.lab3.application.command.task_tag.HardDeleteTaskTagCommandHandler;
import com.example.lab3.domain.repository.TaskTagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HardDeleteTaskTagCommandHandlerTest {

    @Mock
    private TaskTagRepository repository;

    @InjectMocks
    private HardDeleteTaskTagCommandHandler useCase;

    @Test
    void handle_ShouldHardDelete() {
        Long id = 1L;

        useCase.handle(new HardDeleteTaskTagCommand(id));

        verify(repository).hardDelete(id);
    }
}
