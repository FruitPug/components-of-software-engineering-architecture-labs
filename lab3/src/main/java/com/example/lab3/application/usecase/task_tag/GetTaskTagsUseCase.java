package com.example.lab3.application.usecase.task_tag;

import com.example.lab3.domain.model.TaskTag;
import com.example.lab3.domain.repository.TaskTagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTaskTagsUseCase {

    private final TaskTagRepository repository;

    @Transactional
    public Page<TaskTag> execute(Long taskId, Long tagId, Pageable pageable) {
        return repository.search(taskId, tagId, pageable);
    }
}
