package com.example.lab3.presentation.mapper;

import com.example.lab3.application.command.user.CreateUserCommand;
import com.example.lab3.domain.model.User;
import com.example.lab3.presentation.dto.request.UserCreateDto;
import com.example.lab3.presentation.dto.response.UserResponseDto;

public class UserDtoMapper {

    public static CreateUserCommand toCommand(UserCreateDto dto) {
        return new CreateUserCommand(
                dto.getEmail(),
                dto.getFullName(),
                dto.getPassword(),
                dto.getRole()
        );
    }

    public static UserResponseDto toResponseDto(User user) {
        return UserResponseDto.builder()
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();
    }
}
