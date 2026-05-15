package com.example.lab4.presentation.dto.response;

import com.example.lab4.domain.enums.ProjectMemberRole;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProjectMemberResponseDto {
    Long projectId;
    Long userId;
    ProjectMemberRole role;
}
