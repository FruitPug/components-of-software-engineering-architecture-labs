package com.example.lab2.sorting_bin.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectCreateDto {

    @NotBlank
    private String name;

    private String description;
}
