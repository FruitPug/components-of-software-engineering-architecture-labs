package com.example.lab3.domain.repository;

import com.example.lab3.domain.model.Tag;

import java.util.Optional;

public interface TagRepository {

    Tag save(Tag tag);

    Optional<Tag> findById(Long id);
}
