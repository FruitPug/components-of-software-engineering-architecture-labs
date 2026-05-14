package com.example.lab3.application.query.tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagReadRepository {

    Page<TagReadModel> search(String color, Pageable pageable);
}
