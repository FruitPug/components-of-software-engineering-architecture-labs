package com.example.lab1.controller;

import com.example.lab1.dto.request.UserCreateDto;
import com.example.lab1.dto.response.UserResponseDto;
import com.example.lab1.entity.enums.UserRole;
import com.example.lab1.service.UserService;
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

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getUsersFiltered(
            @RequestParam(required = false) UserRole role,
            Pageable pageable
    ) {
        return userService.getUsersFiltered(role, pageable);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(
            @Valid @RequestBody UserCreateDto dto
    ) {
        return userService.createUser(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteUser(@PathVariable Long id) {
        return userService.softDeleteUser(id);
    }
}
