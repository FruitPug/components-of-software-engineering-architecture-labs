package com.example.lab3.application.query.project;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetProjectsQueryHandler {

    private final ProjectReadRepository repository;

    @Transactional
    public Page<ProjectReadModel> handle(
            GetProjectsQuery query
    ) {
        return repository.search(
                query.status(),
                query.pageable()
        );
    }
}
