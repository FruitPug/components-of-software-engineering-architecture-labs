package com.example.lab2.sorting_bin.service;

import com.example.lab2.infrastructure.persistence.repository.JpaUserRepository;
import com.example.lab2.sorting_bin.dto.request.TaskCreateDto;
import com.example.lab2.sorting_bin.dto.request.TaskReassignDto;
import com.example.lab2.sorting_bin.dto.request.TaskStatusUpdateDto;
import com.example.lab2.sorting_bin.dto.response.TaskResponseDto;
import com.example.lab2.sorting_bin.entity.ProjectEntity;
import com.example.lab2.sorting_bin.entity.TaskEntity;
import com.example.lab2.infrastructure.persistence.entity.UserEntity;
import com.example.lab2.domain.enums.TaskPriority;
import com.example.lab2.domain.enums.TaskStatus;
import com.example.lab2.sorting_bin.mapper.TaskMapper;
import com.example.lab2.sorting_bin.repository.*;
import com.example.lab2.sorting_bin.service.helper.SoftDeleteHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final ProjectRepository projectRepository;
    private final JpaUserRepository jpaUserRepository;
    private final TaskRepository taskRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final TaskCommentRepository taskCommentRepository;
    private final SoftDeleteHelper softDeleteHelper;

    @Transactional
    public void createTask(TaskCreateDto dto) {

        ProjectEntity project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        UserEntity creator = jpaUserRepository.findById(dto.getCreatorUserId())
                .orElseThrow(() -> new IllegalArgumentException("Creator user not found"));

        UserEntity assignee = null;
        if (dto.getAssigneeUserId() != null) {
            assignee = jpaUserRepository.findById(dto.getAssigneeUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Assignee user not found"));
        }

        TaskEntity task = TaskMapper.createTaskEntity(project, creator, assignee, dto);
        taskRepository.save(task);
    }

    @Transactional
    public void updateTaskStatus(TaskStatusUpdateDto dto) {
        TaskEntity task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (task.getStatus() == TaskStatus.DONE && dto.getStatus() == TaskStatus.TODO) {
            throw new IllegalStateException("Cannot move task from DONE back to TODO");
        }

        task.setStatus(dto.getStatus());
        task.setUpdatedAt(LocalDateTime.now());

        taskRepository.save(task);
    }

    @Transactional
    public void reassignTask(TaskReassignDto dto) {

        TaskEntity task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        UserEntity newAssignee = jpaUserRepository.findById(dto.getNewAssigneeUserId())
                .orElseThrow(() -> new IllegalArgumentException("Assignee user not found"));

        Long projectId = task.getProject().getId();

        boolean isMember = projectMemberRepository.existsByProject_IdAndUser_Id(projectId, newAssignee.getId());
        if (!isMember) {
            throw new IllegalStateException("Assignee must be a member of the project");
        }

        task.setAssignee(newAssignee);
        task.setUpdatedAt(LocalDateTime.now());

        taskRepository.save(task);
    }

    @Transactional
    public void softDeleteTask(Long id) {
        softDeleteHelper.softDelete(
                id,
                taskRepository::findById,
                taskRepository::save,
                () -> new IllegalArgumentException("Task not found")
        );

        taskCommentRepository.softDeleteByTaskId(id, LocalDateTime.now());
    }

    @Transactional
    public Page<TaskResponseDto> getTasksFiltered(
            TaskStatus status,
            TaskPriority priority,
            Long projectId,
            Long assigneeId,
            Pageable pageable
    ) {
        Page<TaskEntity> page = taskRepository.searchTasksFiltered(status, priority, projectId, assigneeId, pageable);

        return page.map(TaskMapper::toResponseDto);
    }
}
