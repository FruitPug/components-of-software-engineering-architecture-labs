package com.example.lab2.sorting_bin.mapper;

import com.example.lab2.sorting_bin.dto.request.TagCreateDto;
import com.example.lab2.sorting_bin.dto.response.TagResponseDto;
import com.example.lab2.sorting_bin.entity.TagEntity;

import java.time.LocalDateTime;

public class TagMapper {

    public static TagEntity fromCreateDto(TagCreateDto dto) {
        return TagEntity.builder()
                .name(dto.getName())
                .color(dto.getColor())
                .createdAt(LocalDateTime.now())
                .deleted(false)
                .build();
    }

    public static TagResponseDto toResponseDto(TagEntity tag) {
        return TagResponseDto.builder()
                .name(tag.getName())
                .color(tag.getColor())
                .build();
    }
}
