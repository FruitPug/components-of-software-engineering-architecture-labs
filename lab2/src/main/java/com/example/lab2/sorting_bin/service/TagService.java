package com.example.lab2.sorting_bin.service;

import com.example.lab2.sorting_bin.dto.request.TagCreateDto;
import com.example.lab2.sorting_bin.dto.response.TagResponseDto;
import com.example.lab2.sorting_bin.entity.TagEntity;
import com.example.lab2.sorting_bin.mapper.TagMapper;
import com.example.lab2.sorting_bin.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public void createTag(TagCreateDto dto) {
        TagEntity tag = TagMapper.fromCreateDto(dto);
        tagRepository.save(tag);
    }

    @Transactional
    public void softDeleteTag(Long id) {
        TagEntity tag = tagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tag not found"));

        if (tag.isDeleted()) {
            return;
        }

        tag.setDeleted(true);
        tag.setDeletedAt(LocalDateTime.now());

        tagRepository.save(tag);
    }

    @Transactional
    public Page<TagResponseDto> getTagsFiltered(String color, Pageable pageable) {
        Page<TagEntity> page = tagRepository.searchTagsFiltered(color, pageable);

        return page.map(TagMapper::toResponseDto);
    }
}
