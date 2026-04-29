package com.example.lab2.integration.repository;

import com.example.lab2.EntityCreator;
import com.example.lab2.integration.IntegrationTestBase;
import com.example.lab2.infrastructure.persistence.entity.ProjectEntity;
import com.example.lab2.infrastructure.persistence.repository.JpaProjectRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class JpaProjectRepositoryIT extends IntegrationTestBase {

    @Autowired
    private JpaProjectRepository jpaProjectRepository;

    @Test
    @Transactional
    void findById_returnsSavedProject() {
        ProjectEntity project = EntityCreator.getProjectEntity();
        jpaProjectRepository.save(project);

        Optional<ProjectEntity> found = jpaProjectRepository.findById(project.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(project.getId());
    }

    @Test
    @Transactional
    void findRawById_returnsWithNativeQuery() {
        ProjectEntity project = EntityCreator.getProjectEntity();
        jpaProjectRepository.save(project);

        Optional<ProjectEntity> found = jpaProjectRepository.findRawById(project.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(project.getId());
    }
}
