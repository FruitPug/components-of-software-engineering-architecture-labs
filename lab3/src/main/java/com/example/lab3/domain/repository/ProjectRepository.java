package com.example.lab3.domain.repository;

import com.example.lab3.domain.model.Project;

import java.util.Optional;

public interface ProjectRepository {

    Optional<Project> findById(Long id);

    Project save(Project project);

    void hardDelete(Long id);
}
