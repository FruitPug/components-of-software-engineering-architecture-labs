package com.example.lab2.controller;

import com.example.lab2.dto.request.UserCreateDto;
import com.example.lab2.dto.response.UserResponseDto;
import com.example.lab2.entity.enums.UserRole;
import com.example.lab2.service.UserService;
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
        return ResponseEntity.ok(userService.getUsersFiltered(role, pageable));
    }

    @PostMapping
    public ResponseEntity<Void> createUser(
            @Valid @RequestBody UserCreateDto dto
    ) {
        userService.createUser(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteUser(@PathVariable Long id) {
        userService.softDeleteUser(id);
        return ResponseEntity.ok().build();
    }
}
