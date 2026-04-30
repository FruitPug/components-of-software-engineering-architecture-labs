package com.example.lab2.unit.application.usecase.project_member;

import com.example.lab2.application.usecase.project_member.HardDeleteProjectMemberUseCase;
import com.example.lab2.domain.repository.ProjectMemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HardDeleteProjectMemberUseCaseTest {

    @Mock
    private ProjectMemberRepository repository;

    @InjectMocks
    private HardDeleteProjectMemberUseCase useCase;

    @Test
    void execute_ShouldHardDelete() {
        Long memberId = 1L;

        useCase.execute(memberId);

        verify(repository).hardDelete(memberId);
    }
}
