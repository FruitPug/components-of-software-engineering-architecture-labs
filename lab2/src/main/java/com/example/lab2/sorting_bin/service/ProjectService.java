package com.example.lab2.sorting_bin.service;

import com.example.lab2.sorting_bin.dto.request.ProjectCreateDto;
import com.example.lab2.sorting_bin.dto.request.ProjectCreateWithOwnerDto;
import com.example.lab2.sorting_bin.dto.request.ProjectStatusUpdateDto;
import com.example.lab2.sorting_bin.dto.response.ProjectResponseDto;
import com.example.lab2.sorting_bin.entity.ProjectMemberEntity;
import com.example.lab2.infrastructure.persistence.entity.UserEntity;
import com.example.lab2.domain.enums.ProjectMemberRole;
import com.example.lab2.domain.enums.ProjectStatus;
import com.example.lab2.sorting_bin.mapper.ProjectMapper;
import com.example.lab2.sorting_bin.entity.ProjectEntity;
import com.example.lab2.sorting_bin.mapper.ProjectMemberMapper;
import com.example.lab2.sorting_bin.repository.ProjectMemberRepository;
import com.example.lab2.sorting_bin.repository.ProjectRepository;
import com.example.lab2.sorting_bin.repository.TaskRepository;
import com.example.lab2.infrastructure.persistence.repository.JpaUserRepository;
import com.example.lab2.sorting_bin.service.helper.SoftDeleteHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final JpaUserRepository jpaUserRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final SoftDeleteHelper softDeleteHelper;
    private final TaskRepository taskRepository;

    @Transactional
    public void createProject(ProjectCreateDto projectCreateDto) {
        ProjectEntity project = ProjectMapper.fromCreateDto(projectCreateDto);
        projectRepository.save(project);
    }

    @Transactional
    public void createProjectWithOwner(ProjectCreateWithOwnerDto projectCreateWithOwnerDto) {

        UserEntity owner = jpaUserRepository.findById(projectCreateWithOwnerDto.getOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("Owner user not found"));

        ProjectEntity project = ProjectMapper.fromCreateDto(projectCreateWithOwnerDto);

        ProjectEntity savedProject = projectRepository.save(project);

        ProjectMemberEntity member = ProjectMemberMapper.createProjectMemberEntity(
                savedProject,
                owner,
                ProjectMemberRole.OWNER
        );

        projectMemberRepository.save(member);
    }

    @Transactional
    public void softDeleteProject(Long id) {
        softDeleteHelper.softDelete(
                id,
                projectRepository::findById,
                projectRepository::save,
                () -> new IllegalArgumentException("Project not found")
        );

        taskRepository.softDeleteByProjectId(id, LocalDateTime.now());
    }

    @Transactional
    public void hardDeleteProject(Long id) {
        int affected = projectRepository.hardDeleteById(id);

        if (affected == 0) {
            throw new IllegalArgumentException("Project not found");
        }
    }

    @Transactional
    public void updateProjectStatus(ProjectStatusUpdateDto dto) {
        ProjectEntity project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        project.setStatus(dto.getStatus());
        project.setUpdatedAt(LocalDateTime.now());

        projectRepository.save(project);
    }

    @Transactional
    public Page<ProjectResponseDto> getProjectsFiltered(
            ProjectStatus status,
            Pageable pageable
    ) {
        Page<ProjectEntity> page = projectRepository.searchProjectsFiltered(status, pageable);

        return page.map(ProjectMapper::toResponseDto);
    }
}