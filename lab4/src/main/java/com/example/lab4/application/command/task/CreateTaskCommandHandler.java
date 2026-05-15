package com.example.lab4.application.command.task;

import com.example.lab4.domain.error.DomainError;
import com.example.lab4.domain.model.Task;
import com.example.lab4.domain.repository.ProjectRepository;
import com.example.lab4.domain.repository.TaskRepository;
import com.example.lab4.domain.repository.UserRepository;
import com.example.lab4.domain.service.notification.NotificationService;
import com.example.lab4.domain.service.notification.TaskCreatedNotification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateTaskCommandHandler {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    private final NotificationService notificationService;

    @Transactional
    public void handle(CreateTaskCommand cmd) {

        projectRepository.findById(cmd.projectId())
                .orElseThrow(() -> new DomainError("PROJECT_NOT_FOUND"));

        userRepository.findById(cmd.creatorId())
                .orElseThrow(() -> new DomainError("CREATOR_NOT_FOUND"));

        if (cmd.assigneeId() != null) {
            userRepository.findById(cmd.assigneeId())
                    .orElseThrow(() -> new DomainError("ASSIGNEE_NOT_FOUND"));
        }

        Task task = new Task(
                cmd.projectId(),
                cmd.creatorId(),
                cmd.assigneeId(),
                cmd.title(),
                cmd.description(),
                cmd.priority(),
                cmd.dueDate()
        );

        Task savedTask = taskRepository.save(task);
        task.setId(savedTask.getId());

        try {
            notificationService.sendTaskCreatedNotification(
                new TaskCreatedNotification(
                    task.getId(),
                    task.getTitle(),
                    task.getProjectId(),
                    task.getCreatorId(),
                    task.getAssigneeId()
                )
            );
        } catch (Exception e) {
            log.error(
                "Failed to send task created notification for task {}",
                task.getId(),
                e
            );
        }
    }
}
