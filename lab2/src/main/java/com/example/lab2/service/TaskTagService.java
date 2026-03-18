package com.example.lab2.service;

import com.example.lab2.dto.request.TaskTagCreateDto;
import com.example.lab2.dto.response.TaskTagResponseDto;
import com.example.lab2.entity.TagEntity;
import com.example.lab2.entity.TaskEntity;
import com.example.lab2.entity.TaskTagEntity;
import com.example.lab2.mapper.TaskTagMapper;
import com.example.lab2.repository.TagRepository;
import com.example.lab2.repository.TaskRepository;
import com.example.lab2.repository.TaskTagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskTagService {

    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;
    private final TaskTagRepository taskTagRepository;

    @Transactional
    public void createTaskTag(TaskTagCreateDto dto) {

        TaskEntity task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        TagEntity tag = tagRepository.findById(dto.getTagId())
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
