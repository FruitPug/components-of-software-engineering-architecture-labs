package com.example.lab2.sorting_bin.controller;

import com.example.lab2.sorting_bin.dto.request.TagCreateDto;
import com.example.lab2.sorting_bin.dto.response.TagResponseDto;
import com.example.lab2.sorting_bin.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<Page<TagResponseDto>> getTagsFiltered(
            @RequestParam(required = false) String color,
            Pageable pageable
    ) {
        return ResponseEntity.ok(tagService.getTagsFiltered(color, pageable));
    }

    @PostMapping
    public ResponseEntity<Void> createTag(
            @Valid @RequestBody TagCreateDto dto
    ) {
        tagService.createTag(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteTag(@PathVariable Long id) {
        tagService.softDeleteTag(id);
        return ResponseEntity.ok().build();
    }
}
