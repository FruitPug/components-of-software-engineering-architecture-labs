package com.example.lab2.dto.response;

import com.example.lab2.entity.enums.UserRole;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserResponseDto {
    String fullName;
    UserRole role;
}
