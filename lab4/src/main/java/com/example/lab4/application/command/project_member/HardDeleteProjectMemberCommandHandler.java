package com.example.lab4.application.command.project_member;

import com.example.lab4.domain.repository.ProjectMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HardDeleteProjectMemberCommandHandler {

    private final ProjectMemberRepository repository;

    @Transactional
    public void handle(HardDeleteProjectMemberCommand cmd) {
        repository.hardDelete(cmd.id());
    }
}
