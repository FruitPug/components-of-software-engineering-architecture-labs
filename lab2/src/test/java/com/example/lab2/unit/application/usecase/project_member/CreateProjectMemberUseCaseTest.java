package com.example.lab2.unit.application.usecase.project_member;

import com.example.lab2.application.command.project_member.CreateProjectMemberCommand;
import com.example.lab2.application.usecase.project_member.CreateProjectMemberUseCase;
import com.example.lab2.domain.enums.ProjectMemberRole;
import com.example.lab2.domain.error.DomainError;
import com.example.lab2.domain.model.Project;
import com.example.lab2.domain.model.ProjectMember;
import com.example.lab2.domain.model.User;
import com.example.lab2.domain.repository.ProjectMemberRepository;
import com.example.lab2.domain.repository.ProjectRepository;
import com.example.lab2.domain.repository.UserRepository;
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
class CreateProjectMemberUseCaseTest {

    @Mock
    private ProjectMemberRepository memberRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateProjectMemberUseCase useCase;

    @Test
    void execute_WhenValid_ShouldCreateMember() {
        CreateProjectMemberCommand cmd = new CreateProjectMemberCommand(1L, 2L, ProjectMemberRole.CONTRIBUTOR);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(mock(Project.class)));
        when(userRepository.findById(2L)).thenReturn(Optional.of(mock(User.class)));
        when(memberRepository.exists(1L, 2L)).thenReturn(false);
        when(memberRepository.save(any(ProjectMember.class))).thenReturn(mock(ProjectMember.class));

        useCase.execute(cmd);

        verify(memberRepository).save(any(ProjectMember.class));
    }

    @Test
    void execute_WhenProjectNotFound_ShouldThrowException() {
        CreateProjectMemberCommand cmd = new CreateProjectMemberCommand(1L, 2L, ProjectMemberRole.CONTRIBUTOR);
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.execute(cmd));
        assertEquals("PROJECT_NOT_FOUND", exception.getMessage());
    }

    @Test
    void execute_WhenUserNotFound_ShouldThrowException() {
        CreateProjectMemberCommand cmd = new CreateProjectMemberCommand(1L, 2L, ProjectMemberRole.CONTRIBUTOR);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(mock(Project.class)));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.execute(cmd));
        assertEquals("USER_NOT_FOUND", exception.getMessage());
    }

    @Test
    void execute_WhenRoleIsOwnerAndOwnerExists_ShouldThrowException() {
        CreateProjectMemberCommand cmd = new CreateProjectMemberCommand(1L, 2L, ProjectMemberRole.OWNER);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(mock(Project.class)));
        when(userRepository.findById(2L)).thenReturn(Optional.of(mock(User.class)));
        when(memberRepository.findOwner(1L)).thenReturn(Optional.of(mock(ProjectMember.class)));

        DomainError exception = assertThrows(DomainError.class, () -> useCase.execute(cmd));
        assertEquals("PROJECT_ALREADY_HAS_OWNER", exception.getMessage());
    }

    @Test
    void execute_WhenMemberAlreadyExists_ShouldThrowException() {
        CreateProjectMemberCommand cmd = new CreateProjectMemberCommand(1L, 2L, ProjectMemberRole.CONTRIBUTOR);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(mock(Project.class)));
        when(userRepository.findById(2L)).thenReturn(Optional.of(mock(User.class)));
        when(memberRepository.exists(1L, 2L)).thenReturn(true);

        DomainError exception = assertThrows(DomainError.class, () -> useCase.execute(cmd));
        assertEquals("MEMBER_ALREADY_EXISTS", exception.getMessage());
    }
}
