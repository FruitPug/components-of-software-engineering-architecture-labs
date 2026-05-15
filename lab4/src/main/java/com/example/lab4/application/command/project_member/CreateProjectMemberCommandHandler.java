package com.example.lab4.application.command.project_member;

import com.example.lab4.domain.enums.ProjectMemberRole;
import com.example.lab4.domain.error.DomainError;
import com.example.lab4.domain.model.ProjectMember;
import com.example.lab4.domain.repository.ProjectMemberRepository;
import com.example.lab4.domain.repository.ProjectRepository;
import com.example.lab4.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProjectMemberCommandHandler {

    private final ProjectMemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Transactional
    public void handle(CreateProjectMemberCommand cmd) {

        projectRepository.findById(cmd.projectId())
                .orElseThrow(() -> new DomainError("PROJECT_NOT_FOUND"));

        userRepository.findById(cmd.userId())
                .orElseThrow(() -> new DomainError("USER_NOT_FOUND"));

        if (cmd.role() == ProjectMemberRole.OWNER &&
                memberRepository.findOwner(cmd.projectId()).isPresent()) {
            throw new DomainError("PROJECT_ALREADY_HAS_OWNER");
        }

        if (memberRepository.exists(cmd.projectId(), cmd.userId())) {
            throw new DomainError("MEMBER_ALREADY_EXISTS");
        }

        ProjectMember member = new ProjectMember(
                cmd.projectId(),
                cmd.userId(),
                cmd.role()
        );

        ProjectMember savedMember = memberRepository.save(member);
        member.setId(savedMember.getId());
    }
}
