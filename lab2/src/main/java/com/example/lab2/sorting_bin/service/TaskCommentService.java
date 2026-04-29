package com.example.lab2.sorting_bin.service;

import com.example.lab2.sorting_bin.dto.request.TaskCommentCreateDto;
import com.example.lab2.sorting_bin.dto.response.TaskCommentResponseDto;
import com.example.lab2.sorting_bin.entity.TaskCommentEntity;
import com.example.lab2.infrastructure.persistence.entity.TaskEntity;
import com.example.lab2.infrastructure.persistence.entity.UserEntity;
import com.example.lab2.sorting_bin.mapper.TaskCommentMapper;
import com.example.lab2.sorting_bin.repository.TaskCommentRepository;
import com.example.lab2.infrastructure.persistence.repository.JpaTaskRepository;
import com.example.lab2.infrastructure.persistence.repository.JpaUserRepository;
import com.example.lab2.sorting_bin.service.helper.SoftDeleteHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskCommentService {

    private final JpaTaskRepository jpaTaskRepository;
    private final JpaUserRepository jpaUserRepository;
    private final TaskCommentRepository taskCommentRepository;
    private final SoftDeleteHelper softDeleteHelper;

    @Transactional
    public void createComment(TaskCommentCreateDto dto) {
        TaskEntity task = jpaTaskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        UserEntity author = jpaUserRepository.findById(dto.getAuthorUserId())
                .orElseThrow(() -> new IllegalArgumentException("Author user not found"));

        TaskCommentEntity comment = TaskCommentMapper.createTaskCommentEntity(task, author, dto);
        taskCommentRepository.save(comment);
    }

    @Transactional
    public void softDeleteComment(Long id) {
        softDeleteHelper.softDelete(
                id,
                taskCommentRepository::findById,
                taskCommentRepository::save,
                () -> new IllegalArgumentException("Comment not found")
        );
    }

    @Transactional
    public Page<TaskCommentResponseDto> getCommentsFiltered(
            Long taskId,
            Long userId,
            Pageable pageable
    ) {
        Page<TaskCommentEntity> page = taskCommentRepository.searchCommentsFiltered(
                taskId,
                userId,
                pageable
        );

        return page.map(TaskCommentMapper::toResponseDto);
    }
}
