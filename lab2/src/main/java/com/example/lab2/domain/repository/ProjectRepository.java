package com.example.lab2.domain.repository;

import com.example.lab2.domain.enums.ProjectStatus;
import com.example.lab2.domain.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProjectRepository {

    Optional<Project> findById(Long id);

    Project save(Project project);

    Page<Project> search(ProjectStatus status, Pageable pageable);

    void hardDelete(Long id);
}
