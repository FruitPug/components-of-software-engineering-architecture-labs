package com.example.lab3.application.query.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GetUsersByRoleQueryHandler {

    private final UserReadRepository repository;

    @Transactional
    public Page<UserReadModel> handle(
            GetUsersByRoleQuery query
    ) {
        return repository.findByRole(
                query.role(),
                query.pageable()
        );
    }
}
