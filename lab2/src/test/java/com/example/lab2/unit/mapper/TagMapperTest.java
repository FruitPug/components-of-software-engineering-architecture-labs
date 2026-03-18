package com.example.lab2.unit.mapper;

import com.example.lab2.dto.request.TagCreateDto;
import com.example.lab2.entity.TagEntity;
import com.example.lab2.mapper.TagMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TagMapperTest {

    @Test
    void fromCreateDto_mapsFieldsAndSetsDefaults() {
        TagCreateDto dto = new TagCreateDto();
        dto.setName("backend");
        dto.setColor("red");

        TagEntity entity = TagMapper.fromCreateDto(dto);

        assertThat(entity.getName()).isEqualTo("backend");
        assertThat(entity.getColor()).isEqualTo("red");
        assertThat(entity.isDeleted()).isFalse();
        assertThat(entity.getCreatedAt()).isNotNull();
    }
}
