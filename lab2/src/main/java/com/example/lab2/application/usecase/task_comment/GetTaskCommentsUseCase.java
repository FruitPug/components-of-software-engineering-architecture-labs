package com.example.lab2.application.usecase.task_comment;

import com.example.lab2.domain.model.TaskComment;
import com.example.lab2.domain.repository.TaskCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTaskCommentsUseCase {

    private final TaskCommentRepository repository;

    public Page<TaskComment> execute(Long taskId, Long userId, Pageable pageable) {
        return repository.search(taskId, userId, pageable);
    }
}
