package com.example.lab1.service;

import com.example.lab1.dto.request.ProjectCreateDto;
import com.example.lab1.dto.request.ProjectCreateWithOwnerDto;
import com.example.lab1.dto.request.ProjectStatusUpdateDto;
import com.example.lab1.dto.response.ProjectResponseDto;
import com.example.lab1.entity.ProjectMemberEntity;
import com.example.lab1.entity.UserEntity;
import com.example.lab1.entity.enums.ProjectMemberRole;
import com.example.lab1.entity.enums.ProjectStatus;
import com.example.lab1.mapper.ProjectMapper;
import com.example.lab1.entity.ProjectEntity;
import com.example.lab1.mapper.ProjectMemberMapper;
import com.example.lab1.repository.ProjectMemberRepository;
import com.example.lab1.repository.ProjectRepository;
import com.example.lab1.repository.TaskRepository;
import com.example.lab1.repository.UserRepository;
import com.example.lab1.service.helper.SoftDeleteHelper;
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
    private final UserRepository userRepository;
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

        UserEntity owner = userRepository.findById(projectCreateWithOwnerDto.getOwnerId())
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

        Page<ProjectResponseDto> dtoPage = page.map(ProjectMapper::toResponseDto);

        return dtoPage;
    }
}