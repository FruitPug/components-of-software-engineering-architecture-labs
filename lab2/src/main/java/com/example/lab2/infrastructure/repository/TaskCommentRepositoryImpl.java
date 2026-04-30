package com.example.lab2.infrastructure.repository;

import com.example.lab2.domain.model.TaskComment;
import com.example.lab2.domain.repository.TaskCommentRepository;
import com.example.lab2.infrastructure.mapper.TaskCommentMapper;
import com.example.lab2.infrastructure.persistence.entity.TaskEntity;
import com.example.lab2.infrastructure.persistence.entity.UserEntity;
import com.example.lab2.infrastructure.persistence.repository.JpaTaskCommentRepository;
import com.example.lab2.infrastructure.persistence.repository.JpaTaskRepository;
import com.example.lab2.infrastructure.persistence.repository.JpaUserRepository;
import com.example.lab2.infrastructure.persistence.entity.TaskCommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskCommentRepositoryImpl implements TaskCommentRepository {

    private final JpaTaskCommentRepository jpaRepository;
    private final JpaTaskRepository taskRepository;
    private final JpaUserRepository userRepository;

    @Override
    public TaskComment save(TaskComment comment) {

        TaskEntity task = taskRepository.getReferenceById(comment.getTaskId());
        UserEntity author = userRepository.getReferenceById(comment.getAuthorId());

        TaskCommentEntity entity =
                TaskCommentMapper.toEntity(comment, task, author);

        return TaskCommentMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<TaskComment> findById(Long id) {
        return jpaRepository.findById(id)
                .map(TaskCommentMapper::toDomain);
    }

    @Override
    public Page<TaskComment> search(Long taskId, Long userId, Pageable pageable) {
        return jpaRepository.searchCommentsFiltered(taskId, userId, pageable)
                .map(TaskCommentMapper::toDomain);
    }

    @Override
    public void softDeleteByTaskId(Long taskId) {
        jpaRepository.softDeleteByTaskId(taskId, LocalDateTime.now());
    }
}
