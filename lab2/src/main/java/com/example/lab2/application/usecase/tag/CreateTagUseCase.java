package com.example.lab2.application.usecase.tag;

import com.example.lab2.domain.model.Tag;
import com.example.lab2.domain.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTagUseCase {

    private final TagRepository repository;

    public void execute(String name, String color) {
        Tag tag = new Tag(name, color);
        Tag savedTag = repository.save(tag);
        tag.setId(savedTag.getId());
    }
}
