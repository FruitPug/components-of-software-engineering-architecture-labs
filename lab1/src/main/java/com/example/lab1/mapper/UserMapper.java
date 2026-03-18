package com.example.lab1.mapper;

import com.example.lab1.dto.request.UserCreateDto;
import com.example.lab1.dto.response.UserResponseDto;
import com.example.lab1.entity.UserEntity;

import java.time.LocalDateTime;

public class UserMapper {

    public static UserEntity fromCreateDto(UserCreateDto dto) {
        return UserEntity.builder()
                .email(dto.getEmail())
                .fullName(dto.getFullName())
                .passwordHash(dto.getPassword())
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
