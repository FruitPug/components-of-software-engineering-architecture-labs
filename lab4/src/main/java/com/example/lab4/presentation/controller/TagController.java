package com.example.lab4.presentation.controller;

import com.example.lab4.application.command.tag.CreateTagCommand;
import com.example.lab4.application.command.tag.CreateTagCommandHandler;
import com.example.lab4.application.command.tag.SoftDeleteTagCommand;
import com.example.lab4.application.query.tag.GetTagsQuery;
import com.example.lab4.application.query.tag.GetTagsQueryHandler;
import com.example.lab4.application.command.tag.SoftDeleteTagCommandHandler;
import com.example.lab4.presentation.mapper.TagDtoMapper;
import com.example.lab4.presentation.dto.request.TagCreateDto;
import com.example.lab4.presentation.dto.response.TagResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final CreateTagCommandHandler createUseCase;
    private final SoftDeleteTagCommandHandler deleteUseCase;
    private final GetTagsQueryHandler getUseCase;

    @GetMapping
    public ResponseEntity<Page<TagResponseDto>> getTagsFiltered(
            @RequestParam(required = false) String color,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                getUseCase.handle(new GetTagsQuery(color, pageable))
                        .map(TagDtoMapper::toResponseDto)
        );
    }

    @PostMapping
    public ResponseEntity<Void> createTag(@RequestBody TagCreateDto dto) {
        createUseCase.handle(new CreateTagCommand(dto.getName(), dto.getColor()));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteTag(@PathVariable Long id) {
        deleteUseCase.handle(new SoftDeleteTagCommand(id));
        return ResponseEntity.ok().build();
    }
}
