package com.example.lab2.service;

import com.example.lab2.dto.request.TaskCommentCreateDto;
import com.example.lab2.dto.response.TaskCommentResponseDto;
import com.example.lab2.entity.TaskCommentEntity;
import com.example.lab2.entity.TaskEntity;
import com.example.lab2.entity.UserEntity;
import com.example.lab2.mapper.TaskCommentMapper;
import com.example.lab2.repository.TaskCommentRepository;
import com.example.lab2.repository.TaskRepository;
import com.example.lab2.repository.UserRepository;
import com.example.lab2.service.helper.SoftDeleteHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskCommentService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskCommentRepository taskCommentRepository;
    private final SoftDeleteHelper softDeleteHelper;

    @Transactional
    public void createComment(TaskCommentCreateDto dto) {
        TaskEntity task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        UserEntity author = userRepository.findById(dto.getAuthorUserId())
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
