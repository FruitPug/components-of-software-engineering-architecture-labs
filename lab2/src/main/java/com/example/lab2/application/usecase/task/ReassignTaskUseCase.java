package com.example.lab2.application.usecase.task;

import com.example.lab2.domain.error.DomainError;
import com.example.lab2.domain.model.Task;
import com.example.lab2.domain.repository.ProjectMemberRepository;
import com.example.lab2.domain.repository.TaskRepository;
import com.example.lab2.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReassignTaskUseCase {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectMemberRepository memberRepository;

    @Transactional
    public void execute(Long taskId, Long newAssigneeId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new DomainError("TASK_NOT_FOUND"));

        userRepository.findById(newAssigneeId)
                .orElseThrow(() -> new DomainError("USER_NOT_FOUND"));

        boolean isMember = memberRepository.exists(task.getProjectId(), newAssigneeId);
        if (!isMember) {
            throw new DomainError("ASSIGNEE_NOT_PROJECT_MEMBER");
        }

        task.reassign(newAssigneeId);
        taskRepository.save(task);
    }
}
