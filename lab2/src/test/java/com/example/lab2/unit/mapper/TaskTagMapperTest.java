package com.example.lab2.unit.mapper;

import com.example.lab2.entity.TagEntity;
import com.example.lab2.entity.TaskEntity;
import com.example.lab2.entity.TaskTagEntity;
import com.example.lab2.mapper.TaskTagMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TaskTagMapperTest {

    @Test
    void createTaskTagEntity_setsTaskAndTag() {
        TaskEntity task = TaskEntity.builder()
                .id(1L).build();

        TagEntity tag = TagEntity.builder()
                .id(2L).build();

        TaskTagEntity taskTag = TaskTagMapper.createTaskTagEntity(task, tag);

        assertThat(taskTag.getTask()).isSameAs(task);
        assertThat(taskTag.getTag()).isSameAs(tag);
    }
}
