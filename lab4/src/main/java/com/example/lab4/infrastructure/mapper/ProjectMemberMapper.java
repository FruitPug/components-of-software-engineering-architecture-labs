package com.example.lab4.infrastructure.mapper;

import com.example.lab4.application.query.project_member.ProjectMemberReadModel;
import com.example.lab4.domain.model.ProjectMember;
import com.example.lab4.infrastructure.persistence.entity.ProjectEntity;
import com.example.lab4.infrastructure.persistence.entity.UserEntity;
import com.example.lab4.infrastructure.persistence.entity.ProjectMemberEntity;

public class ProjectMemberMapper {

    public static ProjectMember toDomain(ProjectMemberEntity e) {
        return new ProjectMember(
                e.getId(),
                e.getProject().getId(),
                e.getUser().getId(),
                e.getRole(),
                e.getJoinedAt()
        );
    }

    public static ProjectMemberEntity toEntity(
            ProjectMember member,
            ProjectEntity project,
            UserEntity user
    ) {
        return ProjectMemberEntity.builder()
                .id(member.getId())
                .project(project)
                .user(user)
                .role(member.getRole())
                .joinedAt(member.getJoinedAt())
                .build();
    }

    public static ProjectMemberReadModel toReadModel(ProjectMemberEntity e) {
        return ProjectMemberReadModel.builder()
                .id(e.getId())
                .projectId(e.getProject().getId())
                .userId(e.getUser().getId())
                .role(e.getRole())
                .build();
    }
}
