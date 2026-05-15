package com.example.lab4.application.command.tag;

import com.example.lab4.domain.model.Tag;
import com.example.lab4.domain.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTagCommandHandler {

    private final TagRepository repository;

    @Transactional
    public void handle(CreateTagCommand cmd) {
        Tag tag = new Tag(cmd.name(), cmd.color());
        Tag savedTag = repository.save(tag);
        tag.setId(savedTag.getId());
    }
}
