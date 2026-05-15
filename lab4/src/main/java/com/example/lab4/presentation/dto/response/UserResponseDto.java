package com.example.lab4.presentation.dto.response;

import com.example.lab4.domain.enums.UserRole;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserResponseDto {
    String fullName;
    UserRole role;
}
