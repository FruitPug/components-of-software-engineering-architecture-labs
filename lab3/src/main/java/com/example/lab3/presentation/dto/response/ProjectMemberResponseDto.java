package com.example.lab3.presentation.dto.response;

import com.example.lab3.domain.enums.ProjectMemberRole;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProjectMemberResponseDto {
    Long projectId;
    Long userId;
    ProjectMemberRole role;
}
