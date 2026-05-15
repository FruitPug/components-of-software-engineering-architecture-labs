package com.example.lab4.application.query.task;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTasksQueryHandler {

    private final TaskReadRepository repository;

    @Transactional
    public Page<TaskReadModel> handle(GetTasksQuery query) {
        return repository.search(
                query.status(),
                query.priority(),
                query.projectId(),
                query.assigneeId(),
                query.pageable()
        );
    }
}
