package com.example.lab3.application.query.project_member;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetProjectMembersQueryHandler {

    private final ProjectMemberReadRepository repository;

    @Transactional
    public Page<ProjectMemberReadModel> handle(GetProjectMembersQuery query) {
        return repository.search(query.projectId(), query.userId(), query.role(), query.pageable());
    }
}
