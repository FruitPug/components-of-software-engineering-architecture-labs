package com.example.lab4.application.query.task_tag;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTaskTagsQueryHandler {

    private final TaskTagReadRepository repository;

    @Transactional
    public Page<TaskTagReadModel> handle(GetTaskTagsQuery query) {
        return repository.search(query.taskId(), query.tagId(), query.pageable());
    }
}
