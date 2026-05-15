package com.example.lab4.infrastructure.repository;

import com.example.lab4.application.query.tag.TagReadModel;
import com.example.lab4.application.query.tag.TagReadRepository;
import com.example.lab4.infrastructure.mapper.TagMapper;
import com.example.lab4.infrastructure.persistence.repository.JpaTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TagReadRepositoryImpl implements TagReadRepository {

    private final JpaTagRepository jpaRepository;

    @Override
    public Page<TagReadModel> search(String color, Pageable pageable) {
        return jpaRepository.searchTagsFiltered(color, pageable)
                .map(TagMapper::toReadModel);
    }
}
