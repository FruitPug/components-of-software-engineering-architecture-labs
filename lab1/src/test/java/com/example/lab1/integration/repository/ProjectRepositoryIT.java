package com.example.lab1.integration.repository;

import com.example.lab1.EntityCreator;
import com.example.lab1.integration.IntegrationTestBase;
import com.example.lab1.entity.ProjectEntity;
import com.example.lab1.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectRepositoryIT extends IntegrationTestBase {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    @Transactional
    void findById_returnsSavedProject() {
        ProjectEntity project = EntityCreator.getProjectEntity();
        projectRepository.save(project);

        Optional<ProjectEntity> found = projectRepository.findById(project.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(project.getId());
    }

    @Test
    @Transactional
    void findRawById_returnsWithNativeQuery() {
        ProjectEntity project = EntityCreator.getProjectEntity();
        projectRepository.save(project);

        Optional<ProjectEntity> found = projectRepository.findRawById(project.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(project.getId());
    }
}
