package com.example.lab2.dto.response;

import com.example.lab2.entity.enums.ProjectMemberRole;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProjectMemberResponseDto {
    Long projectId;
    Long userId;
    ProjectMemberRole role;
}
