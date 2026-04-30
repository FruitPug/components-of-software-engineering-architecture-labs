package com.example.lab2.application.usecase.task_tag;

import com.example.lab2.domain.error.DomainError;
import com.example.lab2.domain.model.TaskTag;
import com.example.lab2.domain.repository.TagRepository;
import com.example.lab2.domain.repository.TaskRepository;
import com.example.lab2.domain.repository.TaskTagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTaskTagUseCase {

    private final TaskTagRepository taskTagRepository;
    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;

    @Transactional
    public void execute(CreateTaskTagCommand cmd) {

        taskRepository.findById(cmd.taskId())
                .orElseThrow(() -> new DomainError("TASK_NOT_FOUND"));

        tagRepository.findById(cmd.tagId())
                .orElseThrow(() -> new DomainError("TAG_NOT_FOUND"));

        if (taskTagRepository.exists(cmd.taskId(), cmd.tagId())) {
            throw new DomainError("TASK_TAG_ALREADY_EXISTS");
        }

        TaskTag taskTag = new TaskTag(cmd.taskId(), cmd.tagId());
        TaskTag savedTaskTag = taskTagRepository.save(taskTag);
        taskTag.setId(savedTaskTag.getId());
    }
}
