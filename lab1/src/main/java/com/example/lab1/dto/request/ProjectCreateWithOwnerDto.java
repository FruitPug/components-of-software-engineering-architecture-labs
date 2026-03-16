package com.example.lab1.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectCreateWithOwnerDto {

    @NotBlank
    private String name;

    @NotBlank
    private Long ownerId;

    private String description;
}
