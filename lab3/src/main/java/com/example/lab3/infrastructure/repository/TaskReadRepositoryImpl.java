package com.example.lab3.infrastructure.repository;

import com.example.lab3.application.query.task.TaskReadModel;
import com.example.lab3.application.query.task.TaskReadRepository;
import com.example.lab3.domain.enums.TaskPriority;
import com.example.lab3.domain.enums.TaskStatus;
import com.example.lab3.infrastructure.mapper.TaskMapper;
import com.example.lab3.infrastructure.persistence.repository.JpaTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TaskReadRepositoryImpl implements TaskReadRepository {

    private final JpaTaskRepository jpaRepository;

    @Override
    public Page<TaskReadModel> search(TaskStatus status, TaskPriority priority,
                                      Long projectId, Long assigneeId, Pageable pageable) {
        return jpaRepository.searchTasksFiltered(status, priority, projectId, assigneeId, pageable)
                .map(TaskMapper::toReadModel);
    }
}
