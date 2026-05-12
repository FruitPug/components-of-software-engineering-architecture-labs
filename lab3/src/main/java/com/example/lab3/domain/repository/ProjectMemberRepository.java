package com.example.lab3.domain.repository;

import com.example.lab3.domain.enums.ProjectMemberRole;
import com.example.lab3.domain.model.ProjectMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProjectMemberRepository {

    ProjectMember save(ProjectMember member);

    Optional<ProjectMember> findOwner(Long projectId);

    boolean exists(Long projectId, Long userId);

    Page<ProjectMember> search(
            Long projectId,
            Long userId,
            ProjectMemberRole role,
            Pageable pageable
    );

    void hardDelete(Long id);
}
