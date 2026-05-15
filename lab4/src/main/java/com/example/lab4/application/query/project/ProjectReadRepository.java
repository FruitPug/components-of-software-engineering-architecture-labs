package com.example.lab4.application.query.project;

import com.example.lab4.domain.enums.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectReadRepository {

    Page<ProjectReadModel> search(
            ProjectStatus status,
            Pageable pageable
    );
}
