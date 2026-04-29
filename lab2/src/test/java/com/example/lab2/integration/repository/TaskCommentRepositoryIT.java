package com.example.lab2.integration.repository;

import com.example.lab2.EntityCreator;
import com.example.lab2.integration.IntegrationTestBase;
import com.example.lab2.infrastructure.persistence.entity.ProjectEntity;
import com.example.lab2.sorting_bin.entity.TaskCommentEntity;
import com.example.lab2.infrastructure.persistence.entity.TaskEntity;
import com.example.lab2.infrastructure.persistence.entity.UserEntity;
import com.example.lab2.infrastructure.persistence.repository.JpaProjectRepository;
import com.example.lab2.sorting_bin.repository.TaskCommentRepository;
import com.example.lab2.infrastructure.persistence.repository.JpaTaskRepository;
import com.example.lab2.infrastructure.persistence.repository.JpaUserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class TaskCommentRepositoryIT extends IntegrationTestBase {

    @Autowired
    private TaskCommentRepository taskCommentRepository;

    @Autowired
    private JpaTaskRepository jpaTaskRepository;

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Autowired
    private JpaProjectRepository jpaProjectRepository;

    @Test
    @Transactional
    void findByAuthorAndTask_returnsSavedComment() {
        UserEntity user = EntityCreator.getUserEntity();
        jpaUserRepository.save(user);

        ProjectEntity project = EntityCreator.getProjectEntity();
        jpaProjectRepository.save(project);

        TaskEntity task = EntityCreator.getTaskEntity(user, project);
        jpaTaskRepository.save(task);

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
        jpaUserRepository.save(user);

        ProjectEntity project = EntityCreator.getProjectEntity();
        jpaProjectRepository.save(project);

        TaskEntity task = EntityCreator.getTaskEntity(user, project);
        jpaTaskRepository.save(task);

        TaskCommentEntity taskComment = EntityCreator.getTaskCommentEntity(user, task);
        taskCommentRepository.save(taskComment);

        Optional<TaskCommentEntity> found = taskCommentRepository.findRawById(taskComment.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(taskComment.getId());
    }
}
