package com.example.lab2.sorting_bin.mapper;

import com.example.lab2.presentation.dto.request.UserCreateDto;
import com.example.lab2.presentation.dto.response.UserResponseDto;
import com.example.lab2.infrastructure.persistence.entity.UserEntity;

import java.time.LocalDateTime;

public class UserMapper {

    public static UserEntity fromCreateDto(UserCreateDto dto) {
        return UserEntity.builder()
                .email(dto.getEmail())
                .fullName(dto.getFullName())
                .role(dto.getRole())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deleted(false)
                .build();
    }

    public static UserResponseDto toResponseDto(UserEntity user) {
        return UserResponseDto.builder()
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();
    }
}
