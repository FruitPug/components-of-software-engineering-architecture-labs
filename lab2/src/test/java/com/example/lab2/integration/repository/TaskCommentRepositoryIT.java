package com.example.lab2.integration.repository;

import com.example.lab2.EntityCreator;
import com.example.lab2.integration.IntegrationTestBase;
import com.example.lab2.entity.ProjectEntity;
import com.example.lab2.entity.TaskCommentEntity;
import com.example.lab2.entity.TaskEntity;
import com.example.lab2.entity.UserEntity;
import com.example.lab2.repository.ProjectRepository;
import com.example.lab2.repository.TaskCommentRepository;
import com.example.lab2.repository.TaskRepository;
import com.example.lab2.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class TaskCommentRepositoryIT extends IntegrationTestBase {

    @Autowired
    private TaskCommentRepository taskCommentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    @Transactional
    void findByAuthorAndTask_returnsSavedComment() {
        UserEntity user = EntityCreator.getUserEntity();
        userRepository.save(user);

        ProjectEntity project = EntityCreator.getProjectEntity();
        projectRepository.save(project);

        TaskEntity task = EntityCreator.getTaskEntity(user, project);
        taskRepository.save(task);

        TaskCommentEntity taskComment = EntityCreator.getTaskCommentEntity(user, task);
        taskCommentRepository.save(taskComment);

        Optional<TaskCommentEntity> found = taskCommentRepository.findByAuthorAndTask(user, task);

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(taskComment.getId());
    }

    @Test
    @Transactional
    void findRawById_returnsWithNativeQuery() {
        UserEntity user = EntityCreator.getUserEntity();
        userRepository.save(user);

        ProjectEntity project = EntityCreator.getProjectEntity();
        projectRepository.save(project);

        TaskEntity task = EntityCreator.getTaskEntity(user, project);
        taskRepository.save(task);

        TaskCommentEntity taskComment = EntityCreator.getTaskCommentEntity(user, task);
        taskCommentRepository.save(taskComment);

        Optional<TaskCommentEntity> found = taskCommentRepository.findRawById(taskComment.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(taskComment.getId());
    }
}
