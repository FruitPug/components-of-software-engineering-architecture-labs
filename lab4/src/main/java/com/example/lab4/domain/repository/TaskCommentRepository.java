package com.example.lab4.domain.repository;

import com.example.lab4.domain.model.TaskComment;

import java.util.Optional;

public interface TaskCommentRepository {

    TaskComment save(TaskComment comment);

    Optional<TaskComment> findById(Long id);
}
