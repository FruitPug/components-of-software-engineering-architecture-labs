package com.example.lab2.unit.mapper;

import com.example.lab2.presentation.dto.request.ProjectCreateDto;
import com.example.lab2.infrastructure.persistence.entity.ProjectEntity;
import com.example.lab2.domain.enums.ProjectStatus;
import com.example.lab2.sorting_bin.mapper.ProjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectMapperTest {

    @Test
    void fromCreateDto_mapsFieldsCorrectlyAndSetsDefaults() {
        ProjectCreateDto dto = new ProjectCreateDto();
        dto.setName("Project name");
        dto.setDescription("Description");

        ProjectEntity entity = ProjectMapper.fromCreateDto(dto);

        assertThat(entity.getName()).isEqualTo("Project name");
        assertThat(entity.getDescription()).isEqualTo("Description");
        assertThat(entity.getStatus()).isEqualTo(ProjectStatus.ACTIVE);
        assertThat(entity.isDeleted()).isFalse();
        assertThat(entity.getCreatedAt()).isNotNull();
        assertThat(entity.getUpdatedAt()).isNotNull();
    }
}
