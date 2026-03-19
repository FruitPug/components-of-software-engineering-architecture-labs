package com.example.lab2.infrastructure.repository;

import com.example.lab2.domain.model.User;
import com.example.lab2.domain.repository.UserRepository;
import com.example.lab2.infrastructure.mapper.UserMapper;
import com.example.lab2.infrastructure.persistence.entity.UserEntity;
import com.example.lab2.infrastructure.persistence.repository.JpaUserRepository;
import com.example.lab2.domain.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaRepository;

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id)
                .map(UserMapper::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity entity = UserMapper.toEntity(user);
        UserEntity saved = jpaRepository.save(entity);

        return UserMapper.toDomain(saved);
    }

    @Override
    public Page<User> findByRole(UserRole role, Pageable pageable) {
        return jpaRepository.searchUsersFiltered(role, pageable)
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(UserMapper::toDomain);
    }
}
