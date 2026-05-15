package com.example.lab4.integration.repository;

import com.example.lab4.integration.EntityCreator;
import com.example.lab4.integration.IntegrationTestBase;
import com.example.lab4.infrastructure.persistence.entity.UserEntity;
import com.example.lab4.infrastructure.persistence.repository.JpaUserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class JpaUserRepositoryIT extends IntegrationTestBase {

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Test
    @Transactional
    void findById_returnsSavedUser() {
        UserEntity user = EntityCreator.getUserEntity();
        jpaUserRepository.save(user);

        Optional<UserEntity> found = jpaUserRepository.findById(user.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(user.getId());
    }

    @Test
    @Transactional
    void findRawById_returnsWithNativeQuery() {
        UserEntity user = EntityCreator.getUserEntity();
        jpaUserRepository.save(user);

        Optional<UserEntity> found = jpaUserRepository.findRawById(user.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(user.getId());
    }
}
