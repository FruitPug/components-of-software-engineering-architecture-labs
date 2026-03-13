package com.example.lab1.service;

import com.example.lab1.dto.request.ProjectMemberCreateDto;
import com.example.lab1.dto.response.ProjectMemberResponseDto;
import com.example.lab1.entity.ProjectEntity;
import com.example.lab1.entity.ProjectMemberEntity;
import com.example.lab1.entity.UserEntity;
import com.example.lab1.entity.enums.ProjectMemberRole;
import com.example.lab1.mapper.ProjectMemberMapper;
import com.example.lab1.repository.ProjectMemberRepository;
import com.example.lab1.repository.ProjectRepository;
import com.example.lab1.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectMemberService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMemberRepository projectMemberRepository;

    @Transactional
    public ResponseEntity<Void> createProjectMember(ProjectMemberCreateDto dto) {
        ProjectEntity project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        UserEntity user = userRepository.findById(dto.getUserId())
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

        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<Void> hardDeleteProject(Long id) {
        int affected = projectMemberRepository.hardDeleteById(id);

        if (affected == 0) {
            throw new IllegalArgumentException("Project member not found");
        }

        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<Page<ProjectMemberResponseDto>> getProjectMembersFiltered(
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

        Page<ProjectMemberResponseDto> dtoPage = page.map(ProjectMemberMapper::toResponseDto);

        return ResponseEntity.ok(dtoPage);
    }
}
