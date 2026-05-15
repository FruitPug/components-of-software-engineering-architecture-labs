package com.example.lab4.infrastructure.repository;

import com.example.lab4.domain.error.DomainError;
import com.example.lab4.domain.model.ProjectMember;
import com.example.lab4.domain.repository.ProjectMemberRepository;
import com.example.lab4.infrastructure.mapper.ProjectMemberMapper;
import com.example.lab4.infrastructure.persistence.entity.ProjectEntity;
import com.example.lab4.infrastructure.persistence.entity.UserEntity;
import com.example.lab4.infrastructure.persistence.repository.JpaProjectMemberRepository;
import com.example.lab4.infrastructure.persistence.repository.JpaProjectRepository;
import com.example.lab4.infrastructure.persistence.repository.JpaUserRepository;
import com.example.lab4.infrastructure.persistence.entity.ProjectMemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProjectMemberRepositoryImpl implements ProjectMemberRepository {

    private final JpaProjectMemberRepository jpaRepository;
    private final JpaProjectRepository projectRepository;
    private final JpaUserRepository userRepository;

    @Override
    public ProjectMember save(ProjectMember member) {

        ProjectEntity project = projectRepository.getReferenceById(member.getProjectId());
        UserEntity user = userRepository.getReferenceById(member.getUserId());

        ProjectMemberEntity entity =
                ProjectMemberMapper.toEntity(member, project, user);

        return ProjectMemberMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<ProjectMember> findOwner(Long projectId) {
        return jpaRepository.findOwner(projectId)
                .map(ProjectMemberMapper::toDomain);
    }

    @Override
    public boolean exists(Long projectId, Long userId) {
        return jpaRepository.existsByProject_IdAndUser_Id(projectId, userId);
    }

    @Override
    public void hardDelete(Long id) {
        int affected = jpaRepository.hardDeleteById(id);
        if (affected == 0) {
            throw new DomainError("PROJECT_MEMBER_NOT_FOUND");
        }
    }
}
