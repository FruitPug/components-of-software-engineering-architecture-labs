package com.example.lab4.application.command.tag;

import com.example.lab4.domain.error.DomainError;
import com.example.lab4.domain.model.Tag;
import com.example.lab4.domain.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SoftDeleteTagCommandHandler {

    private final TagRepository repository;

    @Transactional
    public void handle(SoftDeleteTagCommand cmd) {
        Tag tag = repository.findById(cmd.id())
                .orElseThrow(() -> new DomainError("TAG_NOT_FOUND"));

        tag.softDelete();
        repository.save(tag);
    }
}
