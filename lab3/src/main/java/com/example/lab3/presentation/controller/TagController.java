package com.example.lab3.presentation.controller;

import com.example.lab3.application.usecase.tag.CreateTagUseCase;
import com.example.lab3.application.usecase.tag.GetTagsUseCase;
import com.example.lab3.application.usecase.tag.SoftDeleteTagUseCase;
import com.example.lab3.presentation.mapper.TagDtoMapper;
import com.example.lab3.presentation.dto.request.TagCreateDto;
import com.example.lab3.presentation.dto.response.TagResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final CreateTagUseCase createUseCase;
    private final SoftDeleteTagUseCase deleteUseCase;
    private final GetTagsUseCase getUseCase;

    @GetMapping
    public ResponseEntity<Page<TagResponseDto>> getTagsFiltered(
            @RequestParam(required = false) String color,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                getUseCase.execute(color, pageable)
                        .map(TagDtoMapper::toResponseDto)
        );
    }

    @PostMapping
    public ResponseEntity<Void> createTag(@RequestBody TagCreateDto dto) {
        createUseCase.execute(dto.getName(), dto.getColor());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteTag(@PathVariable Long id) {
        deleteUseCase.execute(id);
        return ResponseEntity.ok().build();
    }
}
