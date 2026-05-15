package com.example.lab4.application.query.tag;

import org.springframework.data.domain.Pageable;

public record GetTagsQuery(
        String color,
        Pageable pageable
) {
}
