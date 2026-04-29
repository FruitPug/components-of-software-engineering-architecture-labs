package com.example.lab2.integration.scenarios;

import com.example.lab2.EntityCreator;
import com.example.lab2.presentation.dto.request.ProjectMemberCreateDto;
import com.example.lab2.infrastructure.persistence.entity.ProjectEntity;
import com.example.lab2.infrastructure.persistence.entity.ProjectMemberEntity;
import com.example.lab2.infrastructure.persistence.entity.UserEntity;
import com.example.lab2.domain.enums.ProjectMemberRole;
import com.example.lab2.integration.IntegrationTestBase;
import com.example.lab2.infrastructure.persistence.repository.JpaProjectMemberRepository;
import com.example.lab2.infrastructure.persistence.repository.JpaProjectRepository;
import com.example.lab2.infrastructure.persistence.repository.JpaUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ProjectMemberIT extends IntegrationTestBase {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private JpaProjectMemberRepository jpaProjectMemberRepository;
    @Autowired private JpaProjectRepository jpaProjectRepository;
    @Autowired private JpaUserRepository jpaUserRepository;
    @Autowired private EntityManager entityManager;

    @Test
    @Transactional
    void createProjectMember() throws  Exception {
        ProjectEntity project = EntityCreator.getProjectEntity();
        jpaProjectRepository.save(project);

        UserEntity user = EntityCreator.getUserEntity();
        jpaUserRepository.save(user);

        ProjectMemberCreateDto dto = new ProjectMemberCreateDto();
        dto.setProjectId(project.getId());
        dto.setUserId(user.getId());
        dto.setRole(ProjectMemberRole.CONTRIBUTOR);

        mockMvc.perform(post("/project-members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().is2xxSuccessful());

        entityManager.flush();
        entityManager.clear();

        List<ProjectMemberEntity> members = jpaProjectMemberRepository.findAll();
        assertThat(members).hasSize(1);
        ProjectMemberEntity member = members.get(0);
        assertThat(member.getProject().getId()).isEqualTo(project.getId());
        assertThat(member.getUser().getId()).isEqualTo(user.getId());
        assertThat(member.getRole()).isEqualTo(ProjectMemberRole.CONTRIBUTOR);
    }

    @Test
    @Transactional
    void createProjectMember_whenProjectMissing() throws Exception {
        UserEntity user = EntityCreator.getUserEntity();
        jpaUserRepository.save(user);

        ProjectMemberCreateDto dto = new ProjectMemberCreateDto();
        dto.setProjectId(999999L);
        dto.setUserId(user.getId());
        dto.setRole(ProjectMemberRole.CONTRIBUTOR);

        mockMvc.perform(post("/project-members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    void hardDeleteProjectMember_physicallyRemovesRow() throws Exception {
        ProjectEntity project = EntityCreator.getProjectEntity();
        jpaProjectRepository.save(project);

        UserEntity user = EntityCreator.getUserEntity();
        jpaUserRepository.save(user);

        ProjectMemberEntity member = EntityCreator.getProjectMemberEntity(user, project);
        jpaProjectMemberRepository.save(member);

        Long id = member.getId();

        assertThat(jpaProjectMemberRepository.findRawById(id)).isPresent();

        mockMvc.perform(delete("/project-members/{id}/hard", id))
                .andExpect(status().is2xxSuccessful());

        entityManager.flush();
        entityManager.clear();

        assertThat(jpaProjectRepository.findRawById(id)).isEmpty();
    }

    @Test
    @Transactional
    void hardDeleteProjectMember_whenNotFound_throwsException() throws Exception {
        Long nonExistingId = 999999L;

        mockMvc.perform(delete("/project-members/{id}/hard", nonExistingId))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    void getProjectMembersFiltered_filtersByRole() throws Exception {
        ProjectEntity project = EntityCreator.getProjectEntity();
        jpaProjectRepository.save(project);

        UserEntity user1 = EntityCreator.getUserEntity();
        user1.setEmail("user1@test.com");
        user1.setFullName("User 1");
        jpaUserRepository.save(user1);

        UserEntity user2 = EntityCreator.getUserEntity();
        user2.setEmail("user2@test.com");
        user2.setFullName("User 2");
        jpaUserRepository.save(user2);

        UserEntity user3 = EntityCreator.getUserEntity();
        user3.setEmail("user3@test.com");
        user3.setFullName("User 3");
        jpaUserRepository.save(user3);

        ProjectMemberEntity projectMember1 = ProjectMemberEntity.builder()
                .project(project)
                .user(user1)
                .role(ProjectMemberRole.CONTRIBUTOR)
                .joinedAt(LocalDateTime.now())
                .build();
        jpaProjectMemberRepository.save(projectMember1);

        ProjectMemberEntity projectMember2 = ProjectMemberEntity.builder()
                .project(project)
                .user(user2)
                .role(ProjectMemberRole.OWNER)
                .joinedAt(LocalDateTime.now())
                .build();
        jpaProjectMemberRepository.save(projectMember2);

        ProjectMemberEntity projectMember3 = ProjectMemberEntity.builder()
                .project(project)
                .user(user3)
                .role(ProjectMemberRole.MAINTAINER)
                .joinedAt(LocalDateTime.now())
                .build();
        jpaProjectMemberRepository.save(projectMember3);

        mockMvc.perform(get("/project-members")
                        .param("projectId", project.getId().toString())
                        .param("role", "CONTRIBUTOR")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].projectId").value(projectMember1.getProject().getId()))
                .andExpect(jsonPath("$.content[0].userId").value(projectMember1.getUser().getId()))
                .andExpect(jsonPath("$.content[0].role").value(projectMember1.getRole().name()));
    }
}