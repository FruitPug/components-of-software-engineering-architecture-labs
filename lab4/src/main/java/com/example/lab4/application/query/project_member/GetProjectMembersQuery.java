package com.example.lab4.application.query.project_member;

import com.example.lab4.domain.enums.ProjectMemberRole;
import org.springframework.data.domain.Pageable;

public record GetProjectMembersQuery(
        Long projectId,
        Long userId,
        ProjectMemberRole role,
        Pageable pageable
) {
}
