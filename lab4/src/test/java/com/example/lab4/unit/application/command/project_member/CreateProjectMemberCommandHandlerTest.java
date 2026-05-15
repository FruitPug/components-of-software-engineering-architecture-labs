package com.example.lab4.unit.application.command.project_member;

import com.example.lab4.application.command.project_member.CreateProjectMemberCommand;
import com.example.lab4.application.command.project_member.CreateProjectMemberCommandHandler;
import com.example.lab4.domain.enums.ProjectMemberRole;
import com.example.lab4.domain.error.DomainError;
import com.example.lab4.domain.model.Project;
import com.example.lab4.domain.model.ProjectMember;
import com.example.lab4.domain.model.User;
import com.example.lab4.domain.repository.ProjectMemberRepository;
import com.example.lab4.domain.repository.ProjectRepository;
import com.example.lab4.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProjectMemberCommandHandlerTest {

    @Mock
    private ProjectMemberRepository memberRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateProjectMemberCommandHandler useCase;

    @Test
    void handle_WhenValid_ShouldCreateMember() {
        CreateProjectMemberCommand cmd = new CreateProjectMemberCommand(1L, 2L, ProjectMemberRole.CONTRIBUTOR);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(mock(Project.class)));
        when(userRepository.findById(2L)).thenReturn(Optional.of(mock(User.class)));
        when(memberRepository.exists(1L, 2L)).thenReturn(false);
        when(memberRepository.save(any(ProjectMember.class))).thenReturn(mock(ProjectMember.class));

        useCase.handle(cmd);

        verify(memberRepository).save(any(ProjectMember.class));
    }

    @Test
    void handle_WhenProjectNotFound_ShouldThrowException() {
        CreateProjectMemberCommand cmd = new CreateProjectMemberCommand(1L, 2L, ProjectMemberRole.CONTRIBUTOR);
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.handle(cmd));
        assertEquals("PROJECT_NOT_FOUND", exception.getMessage());
    }

    @Test
    void handle_WhenUserNotFound_ShouldThrowException() {
        CreateProjectMemberCommand cmd = new CreateProjectMemberCommand(1L, 2L, ProjectMemberRole.CONTRIBUTOR);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(mock(Project.class)));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.handle(cmd));
        assertEquals("USER_NOT_FOUND", exception.getMessage());
    }

    @Test
    void handle_WhenRoleIsOwnerAndOwnerExists_ShouldThrowException() {
        CreateProjectMemberCommand cmd = new CreateProjectMemberCommand(1L, 2L, ProjectMemberRole.OWNER);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(mock(Project.class)));
        when(userRepository.findById(2L)).thenReturn(Optional.of(mock(User.class)));
        when(memberRepository.findOwner(1L)).thenReturn(Optional.of(mock(ProjectMember.class)));

        DomainError exception = assertThrows(DomainError.class, () -> useCase.handle(cmd));
        assertEquals("PROJECT_ALREADY_HAS_OWNER", exception.getMessage());
    }

    @Test
    void handle_WhenMemberAlreadyExists_ShouldThrowException() {
        CreateProjectMemberCommand cmd = new CreateProjectMemberCommand(1L, 2L, ProjectMemberRole.CONTRIBUTOR);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(mock(Project.class)));
        when(userRepository.findById(2L)).thenReturn(Optional.of(mock(User.class)));
        when(memberRepository.exists(1L, 2L)).thenReturn(true);

        DomainError exception = assertThrows(DomainError.class, () -> useCase.handle(cmd));
        assertEquals("MEMBER_ALREADY_EXISTS", exception.getMessage());
    }
}
