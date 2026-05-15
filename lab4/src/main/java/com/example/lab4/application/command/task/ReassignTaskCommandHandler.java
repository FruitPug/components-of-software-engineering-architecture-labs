package com.example.lab4.application.command.task;

import com.example.lab4.domain.error.DomainError;
import com.example.lab4.domain.event.EventBus;
import com.example.lab4.domain.event.TaskReassignedEvent;
import com.example.lab4.domain.model.Task;
import com.example.lab4.domain.repository.ProjectMemberRepository;
import com.example.lab4.domain.repository.TaskRepository;
import com.example.lab4.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ReassignTaskCommandHandler {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectMemberRepository memberRepository;

    private final EventBus eventBus;

    @Transactional
    public void handle(ReassignTaskCommand cmd) {

        Task task = taskRepository.findById(cmd.taskId())
                .orElseThrow(() -> new DomainError("TASK_NOT_FOUND"));

        userRepository.findById(cmd.newAssigneeId())
                .orElseThrow(() -> new DomainError("USER_NOT_FOUND"));

        boolean isMember = memberRepository.exists(task.getProjectId(), cmd.newAssigneeId());
        if (!isMember) {
            throw new DomainError("ASSIGNEE_NOT_PROJECT_MEMBER");
        }

        long oldAssigneeId = task.getAssigneeId();

        task.reassign(cmd.newAssigneeId());
        Task savedTask = taskRepository.save(task);

        long newAssigneeId = savedTask.getAssigneeId();

        eventBus.publish(
            new TaskReassignedEvent(
                task.getId(),
                task.getTitle(),
                oldAssigneeId,
                newAssigneeId,
                Instant.now()
            )
        );
    }
}
