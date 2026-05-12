package com.example.lab3.infrastructure.repository;

import com.example.lab3.application.query.user.UserReadModel;
import com.example.lab3.application.query.user.UserReadRepository;
import com.example.lab3.domain.enums.UserRole;
import com.example.lab3.infrastructure.persistence.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserReadRepositoryImpl
        implements UserReadRepository {

    private final JpaUserRepository jpaRepository;

    @Override
    public Page<UserReadModel> findByRole(
            UserRole role,
            Pageable pageable
    ) {
        return jpaRepository.searchUsersFiltered(role, pageable)
                .map(entity -> new UserReadModel(
                        entity.getId(),
                        entity.getEmail(),
                        entity.getFullName(),
                        entity.getRole()
                ));
    }
}
