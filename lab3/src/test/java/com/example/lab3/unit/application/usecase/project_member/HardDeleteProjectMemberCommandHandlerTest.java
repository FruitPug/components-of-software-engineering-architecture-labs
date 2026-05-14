package com.example.lab3.unit.application.usecase.project_member;

import com.example.lab3.application.command.project_member.HardDeleteProjectMemberCommand;
import com.example.lab3.application.command.project_member.HardDeleteProjectMemberCommandHandler;
import com.example.lab3.domain.repository.ProjectMemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HardDeleteProjectMemberCommandHandlerTest {

    @Mock
    private ProjectMemberRepository repository;

    @InjectMocks
    private HardDeleteProjectMemberCommandHandler useCase;

    @Test
    void handle_ShouldHardDelete() {
        Long memberId = 1L;

        useCase.handle(new HardDeleteProjectMemberCommand(memberId));

        verify(repository).hardDelete(memberId);
    }
}
