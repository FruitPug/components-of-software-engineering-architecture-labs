package com.example.lab3.application.usecase.task_comment;

import com.example.lab3.domain.error.DomainError;
import com.example.lab3.domain.model.TaskComment;
import com.example.lab3.domain.repository.TaskCommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SoftDeleteTaskCommentUseCase {

    private final TaskCommentRepository repository;

    @Transactional
    public void execute(Long id) {
        TaskComment comment = repository.findById(id)
                .orElseThrow(() -> new DomainError("COMMENT_NOT_FOUND"));

        comment.softDelete();
        repository.save(comment);
    }
}
