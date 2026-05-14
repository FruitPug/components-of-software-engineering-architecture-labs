package com.example.lab3.infrastructure.repository;

import com.example.lab3.domain.model.TaskComment;
import com.example.lab3.domain.repository.TaskCommentRepository;
import com.example.lab3.infrastructure.mapper.TaskCommentMapper;
import com.example.lab3.infrastructure.persistence.entity.TaskEntity;
import com.example.lab3.infrastructure.persistence.entity.UserEntity;
import com.example.lab3.infrastructure.persistence.repository.JpaTaskCommentRepository;
import com.example.lab3.infrastructure.persistence.repository.JpaTaskRepository;
import com.example.lab3.infrastructure.persistence.repository.JpaUserRepository;
import com.example.lab3.infrastructure.persistence.entity.TaskCommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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

}
