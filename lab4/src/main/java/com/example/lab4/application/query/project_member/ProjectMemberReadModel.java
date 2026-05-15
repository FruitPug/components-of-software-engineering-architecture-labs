package com.example.lab4.application.query.project_member;

import com.example.lab4.domain.enums.ProjectMemberRole;
import lombok.Builder;

@Builder
public record ProjectMemberReadModel (
        Long id,
        Long projectId,
        Long userId,
        ProjectMemberRole role
) {
}
