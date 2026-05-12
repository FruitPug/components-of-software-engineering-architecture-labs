package com.example.lab3.infrastructure.mapper;

import com.example.lab3.domain.model.User;
import com.example.lab3.infrastructure.persistence.entity.UserEntity;

public class UserMapper {

    public static User toDomain(UserEntity entity) {
        User user = new User(
                entity.getId(),
                entity.getEmail(),
                entity.getFullName(),
                entity.getPasswordHash(),
                entity.getRole()
        );
        user.setCreatedAt(entity.getCreatedAt());
        user.setUpdatedAt(entity.getUpdatedAt());
        user.setDeleted(entity.isDeleted());
        user.setDeletedAt(entity.getDeletedAt());
        return user;
    }

    public static UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .passwordHash(user.getPasswordHash())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .deleted(user.isDeleted())
                .deletedAt(user.getDeletedAt())
                .build();
    }
}