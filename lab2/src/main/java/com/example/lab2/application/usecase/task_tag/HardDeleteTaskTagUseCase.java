package com.example.lab2.application.usecase.task_tag;

import com.example.lab2.domain.repository.TaskTagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HardDeleteTaskTagUseCase {

    private final TaskTagRepository repository;

    @Transactional
    public void execute(Long id) {
        repository.hardDelete(id);
    }
}
