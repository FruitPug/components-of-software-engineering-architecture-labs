package com.example.lab2.sorting_bin.service;

import com.example.lab2.presentation.dto.request.UserCreateDto;
import com.example.lab2.presentation.dto.response.UserResponseDto;
import com.example.lab2.infrastructure.persistence.entity.UserEntity;
import com.example.lab2.sorting_bin.entity.enums.UserRole;
import com.example.lab2.sorting_bin.mapper.UserMapper;
import com.example.lab2.infrastructure.persistence.repository.JpaUserRepository;
import com.example.lab2.sorting_bin.service.helper.SoftDeleteHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JpaUserRepository jpaUserRepository;
    private final SoftDeleteHelper softDeleteHelper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(UserCreateDto dto) {
        UserEntity user = UserMapper.fromCreateDto(dto);
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        jpaUserRepository.save(user);
    }

    @Transactional
    public void softDeleteUser(Long id) {
        softDeleteHelper.softDelete(
                id,
                jpaUserRepository::findById,
                jpaUserRepository::save,
                () -> new IllegalArgumentException("User not found")
        );
    }

    @Transactional
    public Page<UserResponseDto> getUsersFiltered(UserRole role, Pageable pageable) {
        Page<UserEntity> page = jpaUserRepository.searchUsersFiltered(role, pageable);

        return page.map(UserMapper::toResponseDto);
    }
}
