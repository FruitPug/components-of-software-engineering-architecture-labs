package com.example.lab3.infrastructure.repository;

import com.example.lab3.domain.error.DomainError;
import com.example.lab3.domain.model.Project;
import com.example.lab3.domain.repository.ProjectRepository;
import com.example.lab3.infrastructure.mapper.ProjectMapper;
import com.example.lab3.infrastructure.persistence.repository.JpaProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepository {

    private final JpaProjectRepository jpaRepository;

    @Override
    public Optional<Project> findById(Long id) {
        return jpaRepository.findById(id)
                .map(ProjectMapper::toDomain);
    }

    @Override
    public Project save(Project project) {
        return ProjectMapper.toDomain(
                jpaRepository.save(ProjectMapper.toEntity(project))
        );
    }

    @Override
    public void hardDelete(Long id) {
        int affected = jpaRepository.hardDeleteById(id);
        if (affected == 0) {
            throw new DomainError("PROJECT_NOT_FOUND");
        }
    }
}
