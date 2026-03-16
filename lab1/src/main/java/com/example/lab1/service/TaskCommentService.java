package com.example.lab1.service;

import com.example.lab1.dto.request.TaskCommentCreateDto;
import com.example.lab1.dto.response.TaskCommentResponseDto;
import com.example.lab1.entity.TaskCommentEntity;
import com.example.lab1.entity.TaskEntity;
import com.example.lab1.entity.UserEntity;
import com.example.lab1.mapper.TaskCommentMapper;
import com.example.lab1.repository.TaskCommentRepository;
import com.example.lab1.repository.TaskRepository;
import com.example.lab1.repository.UserRepository;
import com.example.lab1.service.helper.SoftDeleteHelper;
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
