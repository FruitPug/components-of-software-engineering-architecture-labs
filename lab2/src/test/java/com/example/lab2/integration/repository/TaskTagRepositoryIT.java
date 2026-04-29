package com.example.lab2.integration.repository;

import com.example.lab2.EntityCreator;
import com.example.lab2.infrastructure.persistence.entity.ProjectEntity;
import com.example.lab2.infrastructure.persistence.entity.TaskEntity;
import com.example.lab2.infrastructure.persistence.entity.UserEntity;
import com.example.lab2.infrastructure.persistence.repository.JpaTaskRepository;
import com.example.lab2.infrastructure.persistence.repository.JpaUserRepository;
import com.example.lab2.infrastructure.persistence.repository.JpaProjectRepository;
import com.example.lab2.integration.IntegrationTestBase;
import com.example.lab2.sorting_bin.entity.*;
import com.example.lab2.sorting_bin.repository.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class TaskTagRepositoryIT extends IntegrationTestBase {

    @Autowired
    private TaskTagRepository taskTagRepository;

    @Autowired
    private JpaTaskRepository jpaTaskRepository;

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Autowired
    private JpaProjectRepository jpaProjectRepository;

    @Autowired
    private TagRepository tagRepository;

    @Test
    @Transactional
    void findByAuthorAndTask_returnsSavedComment() {
        UserEntity user = EntityCreator.getUserEntity();
        jpaUserRepository.save(user);

        ProjectEntity project = EntityCreator.getProjectEntity();
        jpaProjectRepository.save(project);

        TaskEntity task = EntityCreator.getTaskEntity(user, project);
        jpaTaskRepository.save(task);

        TagEntity tag = EntityCreator.getTagEntity();
        tagRepository.save(tag);

        TaskTagEntity taskTag = EntityCreator.getTaskTagEntity(tag, task);
        taskTagRepository.save(taskTag);

        Optional<TaskTagEntity> found = taskTagRepository.findByTaskAndTag(task, tag);

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(taskTag.getId());
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

        TagEntity tag = EntityCreator.getTagEntity();
        tagRepository.save(tag);

        TaskTagEntity taskTag = EntityCreator.getTaskTagEntity(tag, task);
        taskTagRepository.save(taskTag);

        Optional<TaskTagEntity> found = taskTagRepository.findRawById(taskTag.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(taskTag.getId());
    }
}
