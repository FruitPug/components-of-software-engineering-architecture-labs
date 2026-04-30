package com.example.lab2.application.usecase.tag;

import com.example.lab2.domain.model.Tag;
import com.example.lab2.domain.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTagsUseCase {

    private final TagRepository repository;

    @Transactional
    public Page<Tag> execute(String color, Pageable pageable) {
        return repository.search(color, pageable);
    }
}
