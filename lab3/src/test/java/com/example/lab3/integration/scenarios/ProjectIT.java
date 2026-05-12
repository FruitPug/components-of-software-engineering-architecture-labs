package com.example.lab3.integration.scenarios;

import com.example.lab3.integration.EntityCreator;
import com.example.lab3.presentation.dto.request.ProjectCreateDto;
import com.example.lab3.presentation.dto.request.ProjectCreateWithOwnerDto;
import com.example.lab3.infrastructure.persistence.entity.ProjectEntity;
import com.example.lab3.infrastructure.persistence.entity.ProjectMemberEntity;
import com.example.lab3.infrastructure.persistence.entity.TaskEntity;
import com.example.lab3.infrastructure.persistence.entity.UserEntity;
import com.example.lab3.domain.enums.ProjectMemberRole;
import com.example.lab3.domain.enums.ProjectStatus;
import com.example.lab3.integration.IntegrationTestBase;
import com.example.lab3.infrastructure.persistence.repository.JpaProjectMemberRepository;
import com.example.lab3.infrastructure.persistence.repository.JpaProjectRepository;
import com.example.lab3.infrastructure.persistence.repository.JpaTaskRepository;
import com.example.lab3.infrastructure.persistence.repository.JpaUserRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ProjectIT extends IntegrationTestBase {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private JpaProjectRepository jpaProjectRepository;
    @Autowired private JpaUserRepository jpaUserRepository;
    @Autowired private JpaProjectMemberRepository jpaProjectMemberRepository;
    @Autowired private JpaTaskRepository jpaTaskRepository;

    @Autowired private EntityManager entityManager;

    @Test
    @Transactional
    void createProject() throws Exception {
        ProjectCreateDto dto = new ProjectCreateDto();
        dto.setName("Test Project");
        dto.setDescription("Test description");

        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().is2xxSuccessful());

        entityManager.flush();
        entityManager.clear();

        List<ProjectEntity> projects = jpaProjectRepository.findAll();
        assertThat(projects).hasSize(1);

        ProjectEntity project = projects.get(0);
        assertThat(project.getName()).isEqualTo(dto.getName());
        assertThat(project.getDescription()).isEqualTo(dto.getDescription());
        assertThat(project.isDeleted()).isFalse();
    }

    @Test
    @Transactional
    void createProjectWithOwner() throws Exception {
        UserEntity user = EntityCreator.getUserEntity();
        jpaUserRepository.save(user);

        ProjectCreateWithOwnerDto dto = new ProjectCreateWithOwnerDto();
        dto.setName("Test Project");
        dto.setDescription("Test description");
        dto.setOwnerId(user.getId());

        mockMvc.perform(post("/projects/with-owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().is2xxSuccessful());

        entityManager.flush();
        entityManager.clear();

        List<ProjectEntity> projects = jpaProjectRepository.findAll();
        assertThat(projects).hasSize(1);

        ProjectEntity project = projects.get(0);

        Optional<ProjectMemberEntity> memberEntity =
                jpaProjectMemberRepository.findByUserAndProject(user, project);

        assertThat(memberEntity).isPresent();
        assertThat(project.getName()).isEqualTo(dto.getName());
        assertThat(project.getDescription()).isEqualTo(dto.getDescription());
        assertThat(project.isDeleted()).isFalse();
        assertThat(memberEntity.get().getRole()).isEqualTo(ProjectMemberRole.OWNER);
    }

    @Test
    @Transactional
    void softDeleteProject_marksDeletedAndFiltersFromFindById() throws Exception {
        ProjectEntity project = EntityCreator.getProjectEntity();
        jpaProjectRepository.save(project);

        UserEntity user = EntityCreator.getUserEntity();
        jpaUserRepository.save(user);

        TaskEntity task = EntityCreator.getTaskEntity(user, project);
        jpaTaskRepository.save(task);

        Long id = project.getId();

        assertThat(jpaProjectRepository.findById(id)).isPresent();

        mockMvc.perform(delete("/projects/{id}", id))
                .andExpect(status().is2xxSuccessful());

        entityManager.flush();
        entityManager.clear();

        assertThat(jpaProjectRepository.findById(id)).isEmpty();
        assertThat(jpaTaskRepository.findById(task.getId())).isEmpty();

        Optional<ProjectEntity> rawProject = jpaProjectRepository.findRawById(id);
        assertThat(rawProject).isPresent();
        assertThat(rawProject.get().isDeleted()).isTrue();
        assertThat(rawProject.get().getDeletedAt()).isNotNull();

        Optional<TaskEntity> rawTask = jpaTaskRepository.findRawById(task.getId());
        assertThat(rawTask).isPresent();
        assertThat(rawTask.get().isDeleted()).isTrue();
        assertThat(rawTask.get().getDeletedAt()).isNotNull();
    }

    @Test
    @Transactional
    void hardDeleteProject_physicallyRemovesRow() throws Exception {
        ProjectEntity project = EntityCreator.getProjectEntity();
        jpaProjectRepository.save(project);

        UserEntity user = EntityCreator.getUserEntity();
        jpaUserRepository.save(user);

        TaskEntity task = EntityCreator.getTaskEntity(user, project);
        jpaTaskRepository.save(task);

        ProjectMemberEntity projectMember = EntityCreator.getProjectMemberEntity(user, project);
        jpaProjectMemberRepository.save(projectMember);

        Long id = project.getId();

        assertThat(jpaProjectRepository.findRawById(id)).isPresent();

        mockMvc.perform(delete("/projects/{id}/hard", id))
                .andExpect(status().is2xxSuccessful());

        entityManager.flush();
        entityManager.clear();

        assertThat(jpaProjectRepository.findRawById(id)).isEmpty();
        assertThat(jpaProjectMemberRepository.findRawById(projectMember.getId())).isEmpty();
        assertThat(jpaTaskRepository.findRawById(task.getId())).isEmpty();
    }

    @Test
    @Transactional
    void hardDeleteProject_whenNotFound_returns4xx() throws Exception {
        Long nonExistingId = 999999L;

        mockMvc.perform(delete("/projects/{id}/hard", nonExistingId))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    void getProjectFiltered_filtersByStatusAndExcludesSoftDeleted() throws Exception {
        ProjectEntity project1 = ProjectEntity.builder()
                .name("Test project 1")
                .description("desc1")
                .status(ProjectStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deleted(false)
                .build();
        jpaProjectRepository.save(project1);

        ProjectEntity project2 = ProjectEntity.builder()
                .name("Test project 2")
                .description("desc2")
                .status(ProjectStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deleted(true)
                .deletedAt(LocalDateTime.now())
                .build();
        jpaProjectRepository.save(project2);

        ProjectEntity project3 = ProjectEntity.builder()
                .name("Test project 3")
                .description("desc3")
                .status(ProjectStatus.ARCHIVED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deleted(false)
                .build();
        jpaProjectRepository.save(project3);

        entityManager.flush();
        entityManager.clear();

        mockMvc.perform(get("/projects")
                        .param("status", "ACTIVE")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].name").value(project1.getName()))
                .andExpect(jsonPath("$.content[0].description").value(project1.getDescription()))
                .andExpect(jsonPath("$.content[0].status").value(project1.getStatus().name()));
    }
}