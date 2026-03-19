package com.example.lab2.integration.scenarios;

import com.example.lab2.EntityCreator;
import com.example.lab2.presentation.dto.request.UserCreateDto;
import com.example.lab2.infrastructure.persistence.entity.UserEntity;
import com.example.lab2.domain.enums.UserRole;
import com.example.lab2.integration.IntegrationTestBase;
import com.example.lab2.infrastructure.persistence.repository.JpaUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserIT extends IntegrationTestBase {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private JpaUserRepository jpaUserRepository;
    @Autowired private EntityManager entityManager;
    @Autowired private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    void createUser() throws Exception {
        UserCreateDto dto = new UserCreateDto();
        dto.setEmail("new_user_create@test.com");
        dto.setFullName("Test Tester");
        dto.setRole(UserRole.DEVELOPER);
        dto.setPassword("password");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().is2xxSuccessful());

        entityManager.flush();
        entityManager.clear();

        Optional<UserEntity> user = jpaUserRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(dto.getEmail()))
                .findFirst();
        assertThat(user).isPresent();
        assertThat(user.get().getEmail()).isEqualTo(dto.getEmail());
        assertThat(user.get().getFullName()).isEqualTo(dto.getFullName());
        assertThat(user.get().getRole()).isEqualTo(dto.getRole());
    }

    @Test
    @Transactional
    void softDeleteUser_marksDeletedAndFiltersFromFindById() throws Exception {
        UserEntity user = EntityCreator.getUserEntity();
        user.setEmail("user_delete@test.com");
        jpaUserRepository.save(user);

        Long id = user.getId();

        assertThat(jpaUserRepository.findById(id)).isPresent();

        mockMvc.perform(delete("/users/{id}", id))
                .andExpect(status().is2xxSuccessful());

        entityManager.flush();
        entityManager.clear();

        assertThat(jpaUserRepository.findById(id)).isEmpty();

        UserEntity raw = jdbcTemplate.queryForObject(
                "SELECT id, is_deleted, deleted_at FROM users WHERE id = ?",
                (rs, rowNum) -> UserEntity.builder()
                        .id(rs.getLong("id"))
                        .deleted(rs.getBoolean("is_deleted"))
                        .deletedAt(rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null)
                        .build(),
                id
        );
        assertThat(raw).isNotNull();
        assertThat(raw.isDeleted()).isTrue();
        assertThat(raw.getDeletedAt()).isNotNull();
    }

    @Test
    @Transactional
    void getUsersFiltered_filtersByRoleAndExcludesSoftDeleted() throws Exception {
        UserEntity user1 = UserEntity.builder()
                .email("user1_filtered@test.com")
                .fullName("Test 1 Tester")
                .role(UserRole.DEVELOPER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deleted(false)
                .passwordHash("password")
                .build();
        jpaUserRepository.save(user1);

        UserEntity user2 = UserEntity.builder()
                .email("user2_filtered@test.com")
                .fullName("Test 2 Tester")
                .role(UserRole.DEVELOPER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deleted(true)
                .deletedAt(LocalDateTime.now())
                .passwordHash("password")
                .build();
        jpaUserRepository.save(user2);

        UserEntity user3 = UserEntity.builder()
                .email("user3_filtered@test.com")
                .fullName("Test 3 Tester")
                .role(UserRole.MANAGER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deleted(false)
                .passwordHash("password")
                .build();
        jpaUserRepository.save(user3);

        mockMvc.perform(get("/users")
                        .param("role", "DEVELOPER")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].fullName").value(user1.getFullName()))
                .andExpect(jsonPath("$.content[0].role").value(UserRole.DEVELOPER.name()));
    }
}