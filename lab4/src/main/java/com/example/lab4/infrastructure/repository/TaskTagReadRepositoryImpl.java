package com.example.lab4.infrastructure.repository;

import com.example.lab4.application.query.task_tag.TaskTagReadModel;
import com.example.lab4.application.query.task_tag.TaskTagReadRepository;
import com.example.lab4.infrastructure.mapper.TaskTagMapper;
import com.example.lab4.infrastructure.persistence.repository.JpaTaskTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TaskTagReadRepositoryImpl implements TaskTagReadRepository {

    private final JpaTaskTagRepository jpaRepository;

    @Override
    public Page<TaskTagReadModel> search(Long taskId, Long tagId, Pageable pageable) {
        return jpaRepository.searchTaskTagsFiltered(taskId, tagId, pageable)
                .map(TaskTagMapper::toReadModel);
    }
}
