package com.example.lab2.application.usecase.project_member;

import com.example.lab2.domain.enums.ProjectMemberRole;
import com.example.lab2.domain.model.ProjectMember;
import com.example.lab2.domain.repository.ProjectMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetProjectMembersUseCase {

    private final ProjectMemberRepository repository;

    public Page<ProjectMember> execute(
            Long projectId,
            Long userId,
            ProjectMemberRole role,
            Pageable pageable
    ) {
        return repository.search(projectId, userId, role, pageable);
    }
}
