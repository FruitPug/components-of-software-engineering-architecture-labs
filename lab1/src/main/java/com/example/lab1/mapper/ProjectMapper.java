package com.example.lab1.mapper;

import com.example.lab1.dto.request.ProjectCreateDto;
import com.example.lab1.dto.request.ProjectCreateWithOwnerDto;
import com.example.lab1.dto.response.ProjectResponseDto;
import com.example.lab1.entity.ProjectEntity;
import com.example.lab1.entity.enums.ProjectStatus;

import java.time.LocalDateTime;

public class ProjectMapper {

    public static ProjectEntity fromCreateDto(ProjectCreateDto projectCreateDto){
        return ProjectEntity.builder()
                .name(projectCreateDto.getName())
                .description(projectCreateDto.getDescription())
                .status(ProjectStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deleted(false)
                .build();
    }

    public static ProjectEntity fromCreateDto(ProjectCreateWithOwnerDto projectCreateDto){
        ProjectCreateDto dto = new ProjectCreateDto();
        dto.setName(projectCreateDto.getName());
        dto.setDescription(projectCreateDto.getDescription());

        return ProjectMapper.fromCreateDto(dto);
    }

    public static ProjectResponseDto toResponseDto(ProjectEntity projectEntity){
        return ProjectResponseDto.builder()
                .name(projectEntity.getName())
                .description(projectEntity.getDescription())
                .status(projectEntity.getStatus())
                .build();
    }
}
