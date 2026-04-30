package com.example.lab2.presentation.mapper;

import com.example.lab2.domain.model.Tag;
import com.example.lab2.presentation.dto.response.TagResponseDto;

public class TagDtoMapper {

    public static TagResponseDto toResponseDto(Tag tag) {
        return TagResponseDto.builder()
                .name(tag.getName())
                .color(tag.getColor())
                .build();
    }
}
