package com.example.lab1.unit.mapper;

import com.example.lab1.dto.request.UserCreateDto;
import com.example.lab1.entity.UserEntity;
import com.example.lab1.entity.enums.UserRole;
import com.example.lab1.mapper.UserMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    @Test
    void fromCreateDto_mapsFieldsAndSetsDefaults() {
        UserCreateDto dto = new UserCreateDto();
        dto.setEmail("user@test.com");
        dto.setFullName("Test User");
        dto.setRole(UserRole.MANAGER);

        UserEntity entity = UserMapper.fromCreateDto(dto);

        assertThat(entity.getEmail()).isEqualTo("user@test.com");
        assertThat(entity.getFullName()).isEqualTo("Test User");
        assertThat(entity.getRole()).isEqualTo(UserRole.MANAGER);
        assertThat(entity.isDeleted()).isFalse();
        assertThat(entity.getCreatedAt()).isNotNull();
        assertThat(entity.getUpdatedAt()).isNotNull();
    }
}
