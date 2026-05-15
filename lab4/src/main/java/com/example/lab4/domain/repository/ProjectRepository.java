package com.example.lab4.domain.repository;

import com.example.lab4.domain.model.Project;

import java.util.Optional;

public interface ProjectRepository {

    Optional<Project> findById(Long id);

    Project save(Project project);

    void hardDelete(Long id);
}
