package com.example.lab1.mapper;

import com.example.lab1.dto.response.ProjectMemberResponseDto;
import com.example.lab1.entity.ProjectEntity;
import com.example.lab1.entity.ProjectMemberEntity;
import com.example.lab1.entity.UserEntity;
import com.example.lab1.entity.enums.ProjectMemberRole;

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
