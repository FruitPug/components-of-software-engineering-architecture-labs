package com.example.lab2.integration.repository;

import com.example.lab2.integration.EntityCreator;
import com.example.lab2.infrastructure.persistence.entity.*;
import com.example.lab2.infrastructure.persistence.repository.*;
import com.example.lab2.integration.IntegrationTestBase;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class TaskJpaTagRepositoryIT extends IntegrationTestBase {

    @Autowired
    private JpaTaskTagRepository jpaTaskTagRepository;

    @Autowired
    private JpaTaskRepository jpaTaskRepository;

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Autowired
    private JpaProjectRepository jpaProjectRepository;

    @Autowired
    private JpaTagRepository jpaTagRepository;

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
        jpaTagRepository.save(tag);

        TaskTagEntity taskTag = EntityCreator.getTaskTagEntity(tag, task);
        jpaTaskTagRepository.save(taskTag);

        Optional<TaskTagEntity> found = jpaTaskTagRepository.findByTaskAndTag(task, tag);

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
        jpaTagRepository.save(tag);

        TaskTagEntity taskTag = EntityCreator.getTaskTagEntity(tag, task);
        jpaTaskTagRepository.save(taskTag);

        Optional<TaskTagEntity> found = jpaTaskTagRepository.findRawById(taskTag.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(taskTag.getId());
    }
}
