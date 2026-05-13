package com.example.lab3.infrastructure.repository;

import com.example.lab3.application.query.project.ProjectReadModel;
import com.example.lab3.application.query.project.ProjectReadRepository;
import com.example.lab3.domain.enums.ProjectStatus;
import com.example.lab3.infrastructure.persistence.entity.ProjectEntity;
import com.example.lab3.infrastructure.persistence.repository.JpaProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProjectReadRepositoryImpl implements ProjectReadRepository {

    private final JpaProjectRepository jpaRepository;

    @Override
    public Page<ProjectReadModel> search(
            ProjectStatus status,
            Pageable pageable
    ) {
        return jpaRepository.searchProjectsFiltered(
                status,
                pageable
        ).map(this::toReadModel);
    }

    private ProjectReadModel toReadModel(
            ProjectEntity entity
    ) {
        return new ProjectReadModel(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus()
        );
    }
}
