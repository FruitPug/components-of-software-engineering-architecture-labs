package com.example.lab1.integration.repository;

import com.example.lab1.EntityCreator;
import com.example.lab1.integration.IntegrationTestBase;
import com.example.lab1.entity.UserEntity;
import com.example.lab1.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryIT extends IntegrationTestBase {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void findById_returnsSavedUser() {
        UserEntity user = EntityCreator.getUserEntity();
        userRepository.save(user);

        Optional<UserEntity> found = userRepository.findById(user.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(user.getId());
    }

    @Test
    @Transactional
    void findRawById_returnsWithNativeQuery() {
        UserEntity user = EntityCreator.getUserEntity();
        userRepository.save(user);

        Optional<UserEntity> found = userRepository.findRawById(user.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(user.getId());
    }
}
