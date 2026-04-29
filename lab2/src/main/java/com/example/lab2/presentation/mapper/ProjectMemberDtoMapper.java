package com.example.lab2.presentation.mapper;

import com.example.lab2.domain.model.ProjectMember;
import com.example.lab2.presentation.dto.response.ProjectMemberResponseDto;

public class ProjectMemberDtoMapper {

    public static ProjectMemberResponseDto toResponseDto(ProjectMember m) {
        return ProjectMemberResponseDto.builder()
                .projectId(m.getProjectId())
                .userId(m.getUserId())
                .role(m.getRole())
                .build();
    }
}
