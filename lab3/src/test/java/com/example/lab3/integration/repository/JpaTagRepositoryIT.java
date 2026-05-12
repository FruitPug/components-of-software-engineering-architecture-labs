package com.example.lab3.integration.repository;

import com.example.lab3.integration.EntityCreator;
import com.example.lab3.integration.IntegrationTestBase;
import com.example.lab3.infrastructure.persistence.entity.TagEntity;
import com.example.lab3.infrastructure.persistence.repository.JpaTagRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class JpaTagRepositoryIT extends IntegrationTestBase {

    @Autowired
    private JpaTagRepository jpaTagRepository;

    @Test
    @Transactional
    public void findById_returnsSavedTag(){
        TagEntity tag = EntityCreator.getTagEntity();
        jpaTagRepository.save(tag);

        Optional<TagEntity> found = jpaTagRepository.findById(tag.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(tag.getId());
    }

    @Test
    @Transactional
    void findRawById_returnsWithNativeQuery() {
        TagEntity tag = EntityCreator.getTagEntity();
        jpaTagRepository.save(tag);

        Optional<TagEntity> found = jpaTagRepository.findRawById(tag.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(tag.getId());
    }
}
