package com.example.lab3.presentation.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TagResponseDto {
    String name;
    String color;
}
