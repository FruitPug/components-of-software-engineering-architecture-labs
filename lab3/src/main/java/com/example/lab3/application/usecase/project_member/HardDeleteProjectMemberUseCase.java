package com.example.lab3.application.usecase.project_member;

import com.example.lab3.domain.repository.ProjectMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HardDeleteProjectMemberUseCase {

    private final ProjectMemberRepository repository;

    @Transactional
    public void execute(Long id) {
        repository.hardDelete(id);
    }
}
