package com.example.lab2.sorting_bin.mapper;

import com.example.lab2.sorting_bin.dto.response.ProjectMemberResponseDto;
import com.example.lab2.sorting_bin.entity.ProjectEntity;
import com.example.lab2.sorting_bin.entity.ProjectMemberEntity;
import com.example.lab2.infrastructure.persistence.entity.UserEntity;
import com.example.lab2.domain.enums.ProjectMemberRole;

import java.time.LocalDateTime;

public class ProjectMemberMapper {

    public static ProjectMemberEntity createProjectMemberEntity(
            ProjectEntity project,
            UserEntity user,
            ProjectMemberRole role
    ){
        return ProjectMemberEntity.builder()
                .project(project)
                .user(user)
                .role(role)
                .joinedAt(LocalDateTime.now())
                .build();
    }

    public static ProjectMemberResponseDto toResponseDto(ProjectMemberEntity projectMember) {
        return ProjectMemberResponseDto.builder()
                .projectId(projectMember.getProject().getId())
                .userId(projectMember.getUser().getId())
                .role(projectMember.getRole())
                .build();
    }
}
