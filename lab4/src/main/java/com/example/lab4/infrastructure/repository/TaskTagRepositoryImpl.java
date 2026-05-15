package com.example.lab4.infrastructure.repository;

import com.example.lab4.domain.error.DomainError;
import com.example.lab4.domain.model.TaskTag;
import com.example.lab4.domain.repository.TaskTagRepository;
import com.example.lab4.infrastructure.mapper.TaskTagMapper;
import com.example.lab4.infrastructure.persistence.entity.TagEntity;
import com.example.lab4.infrastructure.persistence.entity.TaskEntity;
import com.example.lab4.infrastructure.persistence.entity.TaskTagEntity;
import com.example.lab4.infrastructure.persistence.repository.JpaTagRepository;
import com.example.lab4.infrastructure.persistence.repository.JpaTaskRepository;
import com.example.lab4.infrastructure.persistence.repository.JpaTaskTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TaskTagRepositoryImpl implements TaskTagRepository {

    private final JpaTaskTagRepository jpaRepository;
    private final JpaTaskRepository taskRepository;
    private final JpaTagRepository tagRepository;

    @Override
    public TaskTag save(TaskTag taskTag) {

        TaskEntity task = taskRepository.getReferenceById(taskTag.getTaskId());
        TagEntity tag = tagRepository.getReferenceById(taskTag.getTagId());

        TaskTagEntity entity = TaskTagMapper.toEntity(taskTag, task, tag);

        return TaskTagMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public boolean exists(Long taskId, Long tagId) {
        return jpaRepository.existsByTask_IdAndTag_Id(taskId, tagId);
    }

    @Override
    public void hardDelete(Long id) {
        int affected = jpaRepository.hardDeleteById(id);
        if (affected == 0) {
            throw new DomainError("TASK_TAG_NOT_FOUND");
        }
    }
}
