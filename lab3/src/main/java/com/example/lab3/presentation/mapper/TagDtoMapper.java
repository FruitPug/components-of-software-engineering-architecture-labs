package com.example.lab3.presentation.mapper;

import com.example.lab3.application.query.tag.TagReadModel;
import com.example.lab3.domain.model.Tag;
import com.example.lab3.presentation.dto.response.TagResponseDto;

public class TagDtoMapper {

    public static TagResponseDto toResponseDto(Tag tag) {
        return TagResponseDto.builder()
                .name(tag.getName())
                .color(tag.getColor())
                .build();
    }

    public static TagResponseDto toResponseDto(TagReadModel tag) {
        return TagResponseDto.builder()
                .name(tag.name())
                .color(tag.color())
                .build();
    }
}
