package com.example.lab3.application.usecase.task_comment;

import com.example.lab3.domain.model.TaskComment;
import com.example.lab3.domain.repository.TaskCommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTaskCommentsUseCase {

    private final TaskCommentRepository repository;

    @Transactional
    public Page<TaskComment> execute(Long taskId, Long userId, Pageable pageable) {
        return repository.search(taskId, userId, pageable);
    }
}
