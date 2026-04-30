package com.example.lab2.application.usecase.task_tag;

import com.example.lab2.domain.model.TaskTag;
import com.example.lab2.domain.repository.TaskTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTaskTagsUseCase {

    private final TaskTagRepository repository;

    public Page<TaskTag> execute(Long taskId, Long tagId, Pageable pageable) {
        return repository.search(taskId, tagId, pageable);
    }
}
