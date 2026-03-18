package com.example.lab2.service;

import com.example.lab2.dto.request.UserCreateDto;
import com.example.lab2.dto.response.UserResponseDto;
import com.example.lab2.entity.UserEntity;
import com.example.lab2.entity.enums.UserRole;
import com.example.lab2.mapper.UserMapper;
import com.example.lab2.repository.UserRepository;
import com.example.lab2.service.helper.SoftDeleteHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SoftDeleteHelper softDeleteHelper;

    @Transactional
    public void createUser(UserCreateDto dto) {
        UserEntity user = UserMapper.fromCreateDto(dto);
        userRepository.save(user);
    }

    @Transactional
    public void softDeleteUser(Long id) {
        softDeleteHelper.softDelete(
                id,
                userRepository::findById,
                userRepository::save,
                () -> new IllegalArgumentException("User not found")
        );
    }

    @Transactional
    public Page<UserResponseDto> getUsersFiltered(UserRole role, Pageable pageable) {
        Page<UserEntity> page = userRepository.searchUsersFiltered(role, pageable);

        return page.map(UserMapper::toResponseDto);
    }
}
