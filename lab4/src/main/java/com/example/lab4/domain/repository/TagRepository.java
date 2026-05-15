package com.example.lab4.domain.repository;

import com.example.lab4.domain.model.Tag;

import java.util.Optional;

public interface TagRepository {

    Tag save(Tag tag);

    Optional<Tag> findById(Long id);
}
