package com.example.lab4.application.query.project_member;

import com.example.lab4.domain.enums.ProjectMemberRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectMemberReadRepository {

    Page<ProjectMemberReadModel> search(
            Long projectId,
            Long userId,
            ProjectMemberRole role,
            Pageable pageable
    );
}
