package com.example.lab2.infrastructure.repository;

import com.example.lab2.domain.model.Tag;
import com.example.lab2.domain.repository.TagRepository;
import com.example.lab2.infrastructure.mapper.TagMapper;
import com.example.lab2.infrastructure.persistence.repository.JpaTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {

    private final JpaTagRepository jpaRepository;

    @Override
    public Tag save(Tag tag) {
        return TagMapper.toDomain(
                jpaRepository.save(TagMapper.toEntity(tag))
        );
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return jpaRepository.findById(id)
                .map(TagMapper::toDomain);
    }

    @Override
    public Page<Tag> search(String color, Pageable pageable) {
        return jpaRepository.searchTagsFiltered(color, pageable)
                .map(TagMapper::toDomain);
    }
}
