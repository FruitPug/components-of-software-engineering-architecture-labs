package com.example.lab2.application.usecase.task_comment;

import com.example.lab2.domain.error.DomainError;
import com.example.lab2.domain.model.TaskComment;
import com.example.lab2.domain.repository.TaskCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SoftDeleteTaskCommentUseCase {

    private final TaskCommentRepository repository;

    public void execute(Long id) {
        TaskComment comment = repository.findById(id)
                .orElseThrow(() -> new DomainError("COMMENT_NOT_FOUND"));

        comment.softDelete();
        repository.save(comment);
    }
}
