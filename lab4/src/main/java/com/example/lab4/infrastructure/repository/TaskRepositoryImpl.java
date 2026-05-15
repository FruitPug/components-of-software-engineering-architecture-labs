package com.example.lab4.infrastructure.repository;

import com.example.lab4.domain.model.Task;
import com.example.lab4.domain.repository.TaskRepository;
import com.example.lab4.infrastructure.mapper.TaskMapper;
import com.example.lab4.infrastructure.persistence.entity.ProjectEntity;
import com.example.lab4.infrastructure.persistence.entity.TaskEntity;
import com.example.lab4.infrastructure.persistence.entity.UserEntity;
import com.example.lab4.infrastructure.persistence.repository.JpaProjectRepository;
import com.example.lab4.infrastructure.persistence.repository.JpaTaskRepository;
import com.example.lab4.infrastructure.persistence.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private final JpaTaskRepository jpaRepository;
    private final JpaProjectRepository projectRepository;
    private final JpaUserRepository userRepository;

    @Override
    public Task save(Task task) {

        ProjectEntity project = projectRepository.getReferenceById(task.getProjectId());
        UserEntity creator = userRepository.getReferenceById(task.getCreatorId());

        UserEntity assignee = task.getAssigneeId() != null
                ? userRepository.getReferenceById(task.getAssigneeId())
                : null;

        TaskEntity entity = TaskMapper.toEntity(task, project, creator, assignee);

        return TaskMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Task> findById(Long id) {
        return jpaRepository.findById(id)
                .map(TaskMapper::toDomain);
    }

    @Override
    public void softDeleteByProjectId(Long projectId) {
        jpaRepository.softDeleteByProjectId(projectId, LocalDateTime.now());
    }
}
