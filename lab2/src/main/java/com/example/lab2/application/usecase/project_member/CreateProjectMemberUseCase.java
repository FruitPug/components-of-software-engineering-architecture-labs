package com.example.lab2.application.usecase.project_member;

import com.example.lab2.domain.enums.ProjectMemberRole;
import com.example.lab2.domain.error.DomainError;
import com.example.lab2.domain.model.ProjectMember;
import com.example.lab2.domain.repository.ProjectMemberRepository;
import com.example.lab2.domain.repository.ProjectRepository;
import com.example.lab2.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProjectMemberUseCase {

    private final ProjectMemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public void execute(CreateProjectMemberCommand cmd) {

        // existence checks
        projectRepository.findById(cmd.projectId())
                .orElseThrow(() -> new DomainError("PROJECT_NOT_FOUND"));

        userRepository.findById(cmd.userId())
                .orElseThrow(() -> new DomainError("USER_NOT_FOUND"));

        // invariant: only one owner
        if (cmd.role() == ProjectMemberRole.OWNER &&
                memberRepository.findOwner(cmd.projectId()).isPresent()) {
            throw new DomainError("PROJECT_ALREADY_HAS_OWNER");
        }

        // prevent duplicates
        if (memberRepository.exists(cmd.projectId(), cmd.userId())) {
            throw new DomainError("MEMBER_ALREADY_EXISTS");
        }

        ProjectMember member = new ProjectMember(
                cmd.projectId(),
                cmd.userId(),
                cmd.role()
        );

        memberRepository.save(member);
    }
}
