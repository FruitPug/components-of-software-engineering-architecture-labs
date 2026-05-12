package com.example.lab3.domain.repository;

import com.example.lab3.domain.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TagRepository {

    Tag save(Tag tag);

    Optional<Tag> findById(Long id);

    Page<Tag> search(String color, Pageable pageable);
}
