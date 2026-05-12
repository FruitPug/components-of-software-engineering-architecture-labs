package com.example.lab3.presentation.dto.response;

import com.example.lab3.domain.enums.UserRole;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserResponseDto {
    String fullName;
    UserRole role;
}
