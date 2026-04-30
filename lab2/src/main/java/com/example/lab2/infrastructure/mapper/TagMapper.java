package com.example.lab2.infrastructure.mapper;

import com.example.lab2.domain.model.Tag;
import com.example.lab2.infrastructure.persistence.entity.TagEntity;

public class TagMapper {

    public static Tag toDomain(TagEntity e) {
        return new Tag(
                e.getId(),
                e.getName(),
                e.getColor(),
                e.isDeleted(),
                e.getCreatedAt(),
                e.getDeletedAt()
        );
    }

    public static TagEntity toEntity(Tag t) {
        return TagEntity.builder()
                .id(t.getId())
                .name(t.getName())
                .color(t.getColor())
                .createdAt(t.getCreatedAt())
                .deleted(t.isDeleted())
                .deletedAt(t.getDeletedAt())
                .build();
    }
}
