package com.example.lab3.application.query.task_comment;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTaskCommentsQueryHandler {

    private final TaskCommentReadRepository repository;

    @Transactional
    public Page<TaskCommentReadModel> handle(GetTaskCommentsQuery query) {
        return repository.search(query.taskId(), query.userId(), query.pageable());
    }
}
