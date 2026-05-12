package com.example.lab3.presentation.dto.request;

import com.example.lab3.domain.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserCreateDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String fullName;

    @NotBlank
    private String password;

    @NotNull
    private UserRole role;
}
