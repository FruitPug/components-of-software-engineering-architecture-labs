package com.example.lab3.application.usecase.project_member;

import com.example.lab3.domain.enums.ProjectMemberRole;
import com.example.lab3.domain.model.ProjectMember;
import com.example.lab3.domain.repository.ProjectMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetProjectMembersUseCase {

    private final ProjectMemberRepository repository;

    @Transactional
    public Page<ProjectMember> execute(
            Long projectId,
            Long userId,
            ProjectMemberRole role,
            Pageable pageable
    ) {
        return repository.search(projectId, userId, role, pageable);
    }
}
