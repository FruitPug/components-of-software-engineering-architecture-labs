package com.example.lab2.integration.repository;

import com.example.lab2.EntityCreator;
import com.example.lab2.integration.IntegrationTestBase;
import com.example.lab2.infrastructure.persistence.entity.ProjectEntity;
import com.example.lab2.infrastructure.persistence.entity.ProjectMemberEntity;
import com.example.lab2.infrastructure.persistence.entity.UserEntity;
import com.example.lab2.domain.enums.ProjectMemberRole;
import com.example.lab2.infrastructure.persistence.repository.JpaProjectMemberRepository;
import com.example.lab2.infrastructure.persistence.repository.JpaProjectRepository;
import com.example.lab2.infrastructure.persistence.repository.JpaUserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class JpaProjectMemberRepositoryIT extends IntegrationTestBase {

    @Autowired
    private JpaProjectMemberRepository jpaProjectMemberRepository;

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Autowired
    private JpaProjectRepository jpaProjectRepository;

    @Test
    @Transactional
    void findByProjectAndUser_returnsSavedRole() {
        UserEntity user = EntityCreator.getUserEntity();
        jpaUserRepository.save(user);

        ProjectEntity project = EntityCreator.getProjectEntity();
        jpaProjectRepository.save(project);

        ProjectMemberEntity projectMember = EntityCreator.getProjectMemberEntity(user, project);
        jpaProjectMemberRepository.save(projectMember);

        Optional<ProjectMemberEntity> found = jpaProjectMemberRepository.findByUserAndProject(user, project);

        assertThat(found).isPresent();
        assertThat(found.get().getRole()).isEqualTo(ProjectMemberRole.OWNER);
    }

    @Test
    @Transactional
    void findRawById_returnsWithNativeQuery() {
        UserEntity user = EntityCreator.getUserEntity();
        jpaUserRepository.save(user);

        ProjectEntity project = EntityCreator.getProjectEntity();
        jpaProjectRepository.save(project);

        ProjectMemberEntity pm = EntityCreator.getProjectMemberEntity(user, project);
        jpaProjectMemberRepository.save(pm);

        Optional<ProjectMemberEntity> found = jpaProjectMemberRepository.findRawById(pm.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(pm.getId());
    }

    @Test
    @Transactional
    void existsByProject_IdAndUser_Id_returnsTrue() {
        UserEntity user = EntityCreator.getUserEntity();
        jpaUserRepository.save(user);

        ProjectEntity project = EntityCreator.getProjectEntity();
        jpaProjectRepository.save(project);

        ProjectMemberEntity pm = EntityCreator.getProjectMemberEntity(user, project);
        jpaProjectMemberRepository.save(pm);

        boolean exists = jpaProjectMemberRepository.existsByProject_IdAndUser_Id(project.getId(), user.getId());
        assertThat(exists).isTrue();

        boolean notExists = jpaProjectMemberRepository.existsByProject_IdAndUser_Id(project.getId(), user.getId() + 1);
        assertThat(notExists).isFalse();
    }
}
