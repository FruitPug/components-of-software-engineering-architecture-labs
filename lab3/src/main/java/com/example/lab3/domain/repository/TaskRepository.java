package com.example.lab3.domain.repository;

import com.example.lab3.domain.model.Task;

import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);

    Optional<Task> findById(Long id);

    void softDeleteByProjectId(Long projectId);
}
