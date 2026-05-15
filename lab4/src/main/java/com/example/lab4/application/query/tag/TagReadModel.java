package com.example.lab4.application.query.tag;

import lombok.Builder;

@Builder
public record TagReadModel(
        Long id,
        String name,
        String color
) {
}
