package com.example.lab3.application.usecase.tag;

import com.example.lab3.domain.model.Tag;
import com.example.lab3.domain.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTagUseCase {

    private final TagRepository repository;

    @Transactional
    public void execute(String name, String color) {
        Tag tag = new Tag(name, color);
        Tag savedTag = repository.save(tag);
        tag.setId(savedTag.getId());
    }
}
