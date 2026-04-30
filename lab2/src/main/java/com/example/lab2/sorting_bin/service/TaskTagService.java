package com.example.lab2.sorting_bin.service;

import com.example.lab2.sorting_bin.dto.request.TaskTagCreateDto;
import com.example.lab2.sorting_bin.dto.response.TaskTagResponseDto;
import com.example.lab2.infrastructure.persistence.entity.TagEntity;
import com.example.lab2.infrastructure.persistence.entity.TaskEntity;
import com.example.lab2.sorting_bin.entity.TaskTagEntity;
import com.example.lab2.sorting_bin.mapper.TaskTagMapper;
import com.example.lab2.infrastructure.persistence.repository.JpaTagRepository;
import com.example.lab2.infrastructure.persistence.repository.JpaTaskRepository;
import com.example.lab2.sorting_bin.repository.TaskTagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskTagService {

    private final JpaTaskRepository jpaTaskRepository;
    private final JpaTagRepository jpaTagRepository;
    private final TaskTagRepository taskTagRepository;

    @Transactional
    public void createTaskTag(TaskTagCreateDto dto) {

        TaskEntity task = jpaTaskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        TagEntity tag = jpaTagRepository.findById(dto.getTagId())
                .orElseThrow(() -> new IllegalArgumentException("Tag not found"));

        TaskTagEntity taskTag = TaskTagMapper.createTaskTagEntity(task, tag);
        taskTagRepository.save(taskTag);
    }

    @Transactional
    public void hardDeleteProject(Long id) {
        int affected = taskTagRepository.hardDeleteById(id);

        if (affected == 0) {
            throw new IllegalArgumentException("Task tag not found");
        }
    }

    @Transactional
    public Page<TaskTagResponseDto> getTaskTagsFiltered(
            Long taskId,
            Long tagId,
            Pageable pageable
    ) {
        Page<TaskTagEntity> page = taskTagRepository.searchTaskTagsFiltered(taskId, tagId, pageable);

        return page.map(TaskTagMapper::toResponseDto);
    }
}
