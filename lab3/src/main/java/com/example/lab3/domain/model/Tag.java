package com.example.lab3.domain.model;

import com.example.lab3.domain.error.DomainError;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class Tag {

    @Setter
    private Long id;
    private final String name;
    private final String color;

    private boolean deleted;
    private final LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public Tag(String name, String color) {
        if (name == null || name.isBlank()) {
            throw new DomainError("TAG_NAME_REQUIRED");
        }

        this.name = name;
        this.color = color;
        this.createdAt = LocalDateTime.now();
        this.deleted = false;
    }

    public Tag(Long id, String name, String color,
               boolean deleted, LocalDateTime createdAt, LocalDateTime deletedAt) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public void softDelete() {
        if (deleted) return;

        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}
