package com.example.lab4.application.command.project;

import com.example.lab4.domain.enums.ProjectMemberRole;
import com.example.lab4.domain.error.DomainError;
import com.example.lab4.domain.model.Project;
import com.example.lab4.domain.model.ProjectMember;
import com.example.lab4.domain.model.User;
import com.example.lab4.domain.repository.ProjectMemberRepository;
import com.example.lab4.domain.repository.ProjectRepository;
import com.example.lab4.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProjectWithOwnerCommandHandler {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository memberRepository;
    private final UserRepository userRepository;

    @Transactional
    public void handle(CreateProjectWithOwnerCommand cmd) {

        User owner = userRepository.findById(cmd.ownerId())
                .orElseThrow(() -> new DomainError("USER_NOT_FOUND"));

        Project project = new Project(cmd.name(), cmd.description());
        Project saved = projectRepository.save(project);

        if (memberRepository.findOwner(saved.getId()).isPresent()) {
            throw new DomainError("PROJECT_ALREADY_HAS_OWNER");
        }

        ProjectMember member = new ProjectMember(
                saved.getId(),
                owner.getId(),
                ProjectMemberRole.OWNER
        );

        ProjectMember savedMember = memberRepository.save(member);
        member.setId(savedMember.getId());
    }
}