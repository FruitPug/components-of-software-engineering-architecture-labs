package com.example.lab2.sorting_bin.dto.response;

import com.example.lab2.domain.enums.ProjectMemberRole;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProjectMemberResponseDto {
    Long projectId;
    Long userId;
    ProjectMemberRole role;
}
