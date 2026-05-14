package com.example.lab3.application.command.task_comment;

import com.example.lab3.domain.error.DomainError;
import com.example.lab3.domain.model.TaskComment;
import com.example.lab3.domain.repository.TaskCommentRepository;
import com.example.lab3.domain.repository.TaskRepository;
import com.example.lab3.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTaskCommentCommandHandler {

    private final TaskCommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Transactional
    public void handle(CreateTaskCommentCommand cmd) {

        taskRepository.findById(cmd.taskId())
                .orElseThrow(() -> new DomainError("TASK_NOT_FOUND"));

        userRepository.findById(cmd.authorId())
                .orElseThrow(() -> new DomainError("USER_NOT_FOUND"));

        TaskComment comment = new TaskComment(
                cmd.taskId(),
                cmd.authorId(),
                cmd.body()
        );

        TaskComment savedComment = commentRepository.save(comment);
        comment.setId(savedComment.getId());
    }
}
