package com.example.lab3.application.command.task_tag;

import com.example.lab3.domain.repository.TaskTagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HardDeleteTaskTagCommandHandler {

    private final TaskTagRepository repository;

    @Transactional
    public void handle(HardDeleteTaskTagCommand cmd) {
        repository.hardDelete(cmd.id());
    }
}
