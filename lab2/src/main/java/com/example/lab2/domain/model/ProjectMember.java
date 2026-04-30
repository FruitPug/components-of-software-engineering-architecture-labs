package com.example.lab2.domain.model;

import com.example.lab2.domain.enums.ProjectMemberRole;
import com.example.lab2.domain.error.DomainError;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class ProjectMember {

    @Setter
    private Long id;
    private final Long projectId;
    private final Long userId;
    private final ProjectMemberRole role;
    private final LocalDateTime joinedAt;

    public ProjectMember(Long projectId, Long userId, ProjectMemberRole role) {
        if (projectId == null || userId == null) {
            throw new DomainError("INVALID_MEMBER_REFERENCE");
        }

        this.projectId = projectId;
        this.userId = userId;
        this.role = role;
        this.joinedAt = LocalDateTime.now();
    }

    public ProjectMember(Long id, Long projectId, Long userId,
                         ProjectMemberRole role, LocalDateTime joinedAt) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.role = role;
        this.joinedAt = joinedAt;
    }
}
