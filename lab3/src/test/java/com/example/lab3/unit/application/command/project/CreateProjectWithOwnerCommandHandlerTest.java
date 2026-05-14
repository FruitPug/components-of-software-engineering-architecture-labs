package com.example.lab3.unit.application.command.project;

import com.example.lab3.application.command.project.CreateProjectWithOwnerCommand;
import com.example.lab3.application.command.project.CreateProjectWithOwnerCommandHandler;
import com.example.lab3.domain.error.DomainError;
import com.example.lab3.domain.model.Project;
import com.example.lab3.domain.model.ProjectMember;
import com.example.lab3.domain.model.User;
import com.example.lab3.domain.repository.ProjectMemberRepository;
import com.example.lab3.domain.repository.ProjectRepository;
import com.example.lab3.domain.repository.UserRepository;
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
class CreateProjectWithOwnerCommandHandlerTest {

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProjectMemberRepository memberRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateProjectWithOwnerCommandHandler useCase;

    @Test
    void handle_WhenValid_ShouldCreateProjectAndMember() {
        CreateProjectWithOwnerCommand cmd = new CreateProjectWithOwnerCommand("P1", "D1", 1L);
        User owner = mock(User.class);
        when(owner.getId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));

        Project savedProject = mock(Project.class);
        when(savedProject.getId()).thenReturn(10L);
        when(projectRepository.save(any(Project.class))).thenReturn(savedProject);
        when(memberRepository.findOwner(10L)).thenReturn(Optional.empty());

        ProjectMember savedMember = mock(ProjectMember.class);
        when(savedMember.getId()).thenReturn(100L);
        when(memberRepository.save(any(ProjectMember.class))).thenReturn(savedMember);

        useCase.handle(cmd);

        verify(projectRepository).save(any(Project.class));
        verify(memberRepository).save(any(ProjectMember.class));
    }

    @Test
    void handle_WhenUserNotFound_ShouldThrowException() {
        CreateProjectWithOwnerCommand cmd = new CreateProjectWithOwnerCommand("P1", "D1", 1L);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        DomainError exception = assertThrows(DomainError.class, () -> useCase.handle(cmd));
        assertEquals("USER_NOT_FOUND", exception.getMessage());
    }

    @Test
    void handle_WhenProjectAlreadyHasOwner_ShouldThrowException() {
        CreateProjectWithOwnerCommand cmd = new CreateProjectWithOwnerCommand("P1", "D1", 1L);
        User owner = mock(User.class);
        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));

        Project savedProject = mock(Project.class);
        when(savedProject.getId()).thenReturn(10L);
        when(projectRepository.save(any(Project.class))).thenReturn(savedProject);
        when(memberRepository.findOwner(10L)).thenReturn(Optional.of(mock(ProjectMember.class)));

        DomainError exception = assertThrows(DomainError.class, () -> useCase.handle(cmd));
        assertEquals("PROJECT_ALREADY_HAS_OWNER", exception.getMessage());
    }
}
