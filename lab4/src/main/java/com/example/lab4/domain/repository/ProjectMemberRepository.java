package com.example.lab4.domain.repository;

import com.example.lab4.domain.model.ProjectMember;

import java.util.Optional;

public interface ProjectMemberRepository {

    ProjectMember save(ProjectMember member);

    Optional<ProjectMember> findOwner(Long projectId);

    boolean exists(Long projectId, Long userId);

    void hardDelete(Long id);
}
