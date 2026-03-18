package com.example.lab2.unit.mapper;

import com.example.lab2.sorting_bin.entity.ProjectEntity;
import com.example.lab2.sorting_bin.entity.ProjectMemberEntity;
import com.example.lab2.infrastructure.persistence.entity.UserEntity;
import com.example.lab2.sorting_bin.entity.enums.ProjectMemberRole;
import com.example.lab2.sorting_bin.mapper.ProjectMemberMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectMemberMapperTest {

    @Test
    void createProjectMemberEntity_setsAllFields() {
        ProjectEntity project = ProjectEntity.builder()
                .id(1L).build();

        UserEntity user = UserEntity.builder()
                .id(2L).build();

        ProjectMemberRole role = ProjectMemberRole.CONTRIBUTOR;

        ProjectMemberEntity member = ProjectMemberMapper.createProjectMemberEntity(
                project,
                user,
                role
        );

        assertThat(member.getProject()).isSameAs(project);
        assertThat(member.getUser()).isSameAs(user);
        assertThat(member.getRole()).isEqualTo(role);
        assertThat(member.getJoinedAt()).isNotNull();
    }
}
