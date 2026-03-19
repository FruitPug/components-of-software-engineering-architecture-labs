package com.example.lab2.presentation.controller;

import com.example.lab2.application.usecase.CreateUserUseCase;
import com.example.lab2.application.usecase.GetUsersByRoleUseCase;
import com.example.lab2.application.usecase.SoftDeleteUserUseCase;
import com.example.lab2.domain.model.User;
import com.example.lab2.presentation.dto.request.UserCreateDto;
import com.example.lab2.presentation.dto.response.UserResponseDto;
import com.example.lab2.presentation.mapper.UserDtoMapper;
import com.example.lab2.domain.enums.UserRole;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final SoftDeleteUserUseCase softDeleteUserUseCase;
    private final GetUsersByRoleUseCase getUsersByRoleUseCase;

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getUsersFiltered(
            @RequestParam(required = false) UserRole role,
            Pageable pageable
    ) {
        Page<User> users = getUsersByRoleUseCase.execute(role, pageable);

        return ResponseEntity.ok(
                users.map(UserDtoMapper::toResponseDto)
        );
    }

    @PostMapping
    public ResponseEntity<Void> createUser(
            @Valid @RequestBody UserCreateDto dto
    ) {
        createUserUseCase.execute(UserDtoMapper.toCommand(dto));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteUser(@PathVariable Long id) {
        softDeleteUserUseCase.execute(id);
        return ResponseEntity.ok().build();
    }
}