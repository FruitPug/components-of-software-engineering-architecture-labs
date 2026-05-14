package com.example.lab3.infrastructure.repository;

import com.example.lab3.application.query.task_comment.TaskCommentReadModel;
import com.example.lab3.application.query.task_comment.TaskCommentReadRepository;
import com.example.lab3.infrastructure.mapper.TaskCommentMapper;
import com.example.lab3.infrastructure.persistence.repository.JpaTaskCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TaskCommentReadRepositoryImpl implements TaskCommentReadRepository {

    private final JpaTaskCommentRepository jpaRepository;

    @Override
    public Page<TaskCommentReadModel> search(Long taskId, Long userId, Pageable pageable) {
        return jpaRepository.searchCommentsFiltered(taskId, userId, pageable)
                .map(TaskCommentMapper::toReadModel);
    }
}
