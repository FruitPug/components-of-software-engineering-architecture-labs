package com.example.lab3.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectCreateDto {

    @NotBlank
    private String name;

    private String description;
}
