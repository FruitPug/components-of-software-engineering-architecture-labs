package com.example.lab2.domain.repository;

import com.example.lab2.domain.model.TaskComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TaskCommentRepository {

    TaskComment save(TaskComment comment);

    Optional<TaskComment> findById(Long id);

    Page<TaskComment> search(Long taskId, Long userId, Pageable pageable);

    void softDeleteByTaskId(Long taskId);

}
