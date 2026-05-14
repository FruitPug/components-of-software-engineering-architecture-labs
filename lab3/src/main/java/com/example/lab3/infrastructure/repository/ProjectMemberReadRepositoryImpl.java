package com.example.lab3.infrastructure.repository;

import com.example.lab3.application.query.project_member.ProjectMemberReadModel;
import com.example.lab3.application.query.project_member.ProjectMemberReadRepository;
import com.example.lab3.domain.enums.ProjectMemberRole;
import com.example.lab3.infrastructure.mapper.ProjectMemberMapper;
import com.example.lab3.infrastructure.persistence.repository.JpaProjectMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProjectMemberReadRepositoryImpl implements ProjectMemberReadRepository {

    private final JpaProjectMemberRepository jpaRepository;

    @Override
    public Page<ProjectMemberReadModel> search(
            Long projectId,
            Long userId,
            ProjectMemberRole role,
            Pageable pageable
    ) {
        return jpaRepository.searchProjectMembersFiltered(projectId, userId, role, pageable)
                .map(ProjectMemberMapper::toReadModel);
    }
}
