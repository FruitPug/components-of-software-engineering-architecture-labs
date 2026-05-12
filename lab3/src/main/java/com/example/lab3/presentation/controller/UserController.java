package com.example.lab3.presentation.controller;

import com.example.lab3.application.command.user.CreateUserCommandHandler;
import com.example.lab3.application.command.user.SoftDeleteUserCommand;
import com.example.lab3.application.query.user.GetUsersByRoleQuery;
import com.example.lab3.application.query.user.GetUsersByRoleQueryHandler;
import com.example.lab3.application.command.user.SoftDeleteUserCommandHandler;
import com.example.lab3.presentation.dto.request.UserCreateDto;
import com.example.lab3.presentation.dto.response.UserResponseDto;
import com.example.lab3.presentation.mapper.UserDtoMapper;
import com.example.lab3.domain.enums.UserRole;
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

    private final CreateUserCommandHandler createUserCommandHandler;
    private final SoftDeleteUserCommandHandler softDeleteUserCommandHandler;
    private final GetUsersByRoleQueryHandler getUsersByRoleQueryHandler;

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getUsersFiltered(
            @RequestParam(required = false) UserRole role,
            Pageable pageable
    ) {
        GetUsersByRoleQuery query =
                new GetUsersByRoleQuery(role, pageable);

        Page<UserResponseDto> response =
                getUsersByRoleQueryHandler.handle(query)
                        .map(UserDtoMapper::toResponseDto);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(
            @Valid @RequestBody UserCreateDto dto
    ) {
        createUserCommandHandler.handle(
                UserDtoMapper.toCommand(dto)
        );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteUser(
            @PathVariable Long id
    ) {
        softDeleteUserCommandHandler.handle(
                new SoftDeleteUserCommand(id)
        );

        return ResponseEntity.ok().build();
    }
}