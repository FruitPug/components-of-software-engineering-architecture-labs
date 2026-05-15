package com.example.lab4.integration.scenarios;

import com.example.lab4.integration.EntityCreator;
import com.example.lab4.presentation.dto.request.TagCreateDto;
import com.example.lab4.infrastructure.persistence.entity.TagEntity;
import com.example.lab4.integration.IntegrationTestBase;
import com.example.lab4.infrastructure.persistence.repository.JpaTagRepository;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TagIT extends IntegrationTestBase {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private JpaTagRepository jpaTagRepository;
    @Autowired private EntityManager entityManager;

    @Test
    @Transactional
    void createTag() throws Exception {
        TagCreateDto dto = new TagCreateDto();
        dto.setName("test_tag");
        dto.setColor("red");

        mockMvc.perform(post("/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().is2xxSuccessful());

        entityManager.flush();
        entityManager.clear();

        List<TagEntity> tags = jpaTagRepository.findAll();
        assertThat(tags).hasSize(1);

        TagEntity tag = tags.get(0);
        assertThat(tag.getName()).isEqualTo(dto.getName());
        assertThat(tag.getColor()).isEqualTo(dto.getColor());
    }

    @Test
    @Transactional
    void softDeleteTag_marksDeletedAndFiltersFromFindById() throws Exception {
        TagEntity tag = EntityCreator.getTagEntity();
        jpaTagRepository.save(tag);

        Long id = tag.getId();

        assertThat(jpaTagRepository.findById(id)).isPresent();

        mockMvc.perform(delete("/tags/{id}", id))
                .andExpect(status().is2xxSuccessful());

        entityManager.flush();
        entityManager.clear();

        assertThat(jpaTagRepository.findById(id)).isEmpty();

        Optional<TagEntity> raw = jpaTagRepository.findRawById(id);
        assertThat(raw).isPresent();
        assertThat(raw.get().isDeleted()).isTrue();
        assertThat(raw.get().getDeletedAt()).isNotNull();
    }

    @Test
    @Transactional
    void getTagsFiltered_filtersByColorAndExcludesSoftDeleted() throws Exception {
        TagEntity tag1 = TagEntity.builder()
                .name("Test tag 1")
                .color("red")
                .createdAt(LocalDateTime.now())
                .deleted(false)
                .build();
        jpaTagRepository.save(tag1);

        TagEntity tag2 = TagEntity.builder()
                .name("Test tag 2")
                .color("red")
                .createdAt(LocalDateTime.now())
                .deleted(true)
                .deletedAt(LocalDateTime.now())
                .build();
        jpaTagRepository.save(tag2);

        TagEntity tag3 = TagEntity.builder()
                .name("Test tag 3")
                .color("green")
                .createdAt(LocalDateTime.now())
                .deleted(false)
                .build();
        jpaTagRepository.save(tag3);

        entityManager.flush();
        entityManager.clear();

        mockMvc.perform(get("/tags")
                        .param("color", "red")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].name").value(tag1.getName()))
                .andExpect(jsonPath("$.content[0].color").value(tag1.getColor()));
    }
}