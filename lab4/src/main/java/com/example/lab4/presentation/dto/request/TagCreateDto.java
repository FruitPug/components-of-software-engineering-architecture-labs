package com.example.lab4.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TagCreateDto {

    @NotBlank
    private String name;

    @NotBlank
    private String color;
}

