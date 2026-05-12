package com.example.lab3.application.usecase.tag;

import com.example.lab3.domain.error.DomainError;
import com.example.lab3.domain.model.Tag;
import com.example.lab3.domain.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SoftDeleteTagUseCase {

    private final TagRepository repository;

    @Transactional
    public void execute(Long id) {
        Tag tag = repository.findById(id)
                .orElseThrow(() -> new DomainError("TAG_NOT_FOUND"));

        tag.softDelete();
        repository.save(tag);
    }
}
