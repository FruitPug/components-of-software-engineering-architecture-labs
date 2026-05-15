package com.example.lab4.presentation.mapper;

import com.example.lab4.application.query.project_member.ProjectMemberReadModel;
import com.example.lab4.domain.model.ProjectMember;
import com.example.lab4.presentation.dto.response.ProjectMemberResponseDto;

public class ProjectMemberDtoMapper {

    public static ProjectMemberResponseDto toResponseDto(ProjectMember m) {
        return ProjectMemberResponseDto.builder()
                .projectId(m.getProjectId())
                .userId(m.getUserId())
                .role(m.getRole())
                .build();
    }

    public static ProjectMemberResponseDto toResponseDto(ProjectMemberReadModel m) {
        return ProjectMemberResponseDto.builder()
                .projectId(m.projectId())
                .userId(m.userId())
                .role(m.role())
                .build();
    }
}
