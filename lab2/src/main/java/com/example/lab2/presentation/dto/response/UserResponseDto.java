package com.example.lab2.presentation.dto.response;

import com.example.lab2.sorting_bin.entity.enums.UserRole;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserResponseDto {
    String fullName;
    UserRole role;
}
