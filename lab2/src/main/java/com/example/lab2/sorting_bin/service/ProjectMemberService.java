package com.example.lab2.sorting_bin.service;

import com.example.lab2.sorting_bin.dto.request.ProjectMemberCreateDto;
import com.example.lab2.sorting_bin.dto.response.ProjectMemberResponseDto;
import com.example.lab2.sorting_bin.entity.ProjectEntity;
import com.example.lab2.sorting_bin.entity.ProjectMemberEntity;
import com.example.lab2.infrastructure.persistence.entity.UserEntity;
import com.example.lab2.domain.enums.ProjectMemberRole;
import com.example.lab2.sorting_bin.mapper.ProjectMemberMapper;
import com.example.lab2.sorting_bin.repository.ProjectMemberRepository;
import com.example.lab2.sorting_bin.repository.ProjectRepository;
import com.example.lab2.infrastructure.persistence.repository.JpaUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectMemberService {

    private final ProjectRepository projectRepository;
    private final JpaUserRepository jpaUserRepository;
    private final ProjectMemberRepository projectMemberRepository;

    @Transactional
    public void createProjectMember(ProjectMemberCreateDto dto) {
        ProjectEntity project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        UserEntity user = jpaUserRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (projectMemberRepository.findByProjectAndRole(project, ProjectMemberRole.OWNER).isPresent()) {
            throw new IllegalStateException("Project can only have one owner");
        }

        ProjectMemberEntity member = ProjectMemberMapper.createProjectMemberEntity(
                project,
                user,
                dto.getRole()
        );

        projectMemberRepository.save(member);
    }

    @Transactional
    public void hardDeleteProject(Long id) {
        int affected = projectMemberRepository.hardDeleteById(id);

        if (affected == 0) {
            throw new IllegalArgumentException("Project member not found");
        }
    }

    @Transactional
    public Page<ProjectMemberResponseDto> getProjectMembersFiltered(
            Long projectId,
            Long userId,
            ProjectMemberRole role,
            Pageable pageable
    ) {
        Page<ProjectMemberEntity> page = projectMemberRepository.searchProjectMembersFiltered(
                projectId,
                userId,
                role,
                pageable
        );

        return page.map(ProjectMemberMapper::toResponseDto);
    }
}
