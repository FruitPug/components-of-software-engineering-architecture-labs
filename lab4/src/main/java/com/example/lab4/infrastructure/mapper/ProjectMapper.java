package com.example.lab4.infrastructure.mapper;

import com.example.lab4.application.query.project.ProjectReadModel;
import com.example.lab4.domain.model.Project;
import com.example.lab4.infrastructure.persistence.entity.ProjectEntity;

public class ProjectMapper {

    public static Project toDomain(ProjectEntity e) {
        return new Project(
                e.getId(),
                e.getName(),
                e.getDescription(),
                e.getStatus(),
                e.isDeleted(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getDeletedAt()
        );
    }

    public static ProjectEntity toEntity(Project p) {
        return ProjectEntity.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .status(p.getStatus())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .deleted(p.isDeleted())
                .deletedAt(p.getDeletedAt())
                .build();
    }

    public static ProjectReadModel toReadModel(ProjectEntity e) {
        return ProjectReadModel.builder()
                .id(e.getId())
                .name(e.getName())
                .description(e.getDescription())
                .status(e.getStatus())
                .build();
    }
}
