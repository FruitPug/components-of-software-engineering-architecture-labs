package com.example.lab2.unit.application.usecase.task_tag;

import com.example.lab2.application.usecase.task_tag.HardDeleteTaskTagUseCase;
import com.example.lab2.domain.repository.TaskTagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HardDeleteTaskTagUseCaseTest {

    @Mock
    private TaskTagRepository repository;

    @InjectMocks
    private HardDeleteTaskTagUseCase useCase;

    @Test
    void execute_ShouldHardDelete() {
        Long id = 1L;

        useCase.execute(id);

        verify(repository).hardDelete(id);
    }
}
