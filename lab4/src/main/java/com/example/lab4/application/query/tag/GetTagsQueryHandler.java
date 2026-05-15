package com.example.lab4.application.query.tag;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTagsQueryHandler {

    private final TagReadRepository repository;

    @Transactional
    public Page<TagReadModel> handle(GetTagsQuery query) {
        return repository.search(query.color(), query.pageable());
    }
}
