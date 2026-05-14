package com.example.lab3.application.command.task;

import com.example.lab3.domain.error.DomainError;
import com.example.lab3.domain.model.Task;
import com.example.lab3.domain.repository.ProjectMemberRepository;
import com.example.lab3.domain.repository.TaskRepository;
import com.example.lab3.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReassignTaskCommandHandler {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectMemberRepository memberRepository;

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

        task.reassign(cmd.newAssigneeId());
        taskRepository.save(task);
    }
}
