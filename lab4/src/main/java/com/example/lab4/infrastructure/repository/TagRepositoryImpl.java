package com.example.lab4.infrastructure.repository;

import com.example.lab4.domain.model.Tag;
import com.example.lab4.domain.repository.TagRepository;
import com.example.lab4.infrastructure.mapper.TagMapper;
import com.example.lab4.infrastructure.persistence.repository.JpaTagRepository;
import lombok.RequiredArgsConstructor;
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
}
