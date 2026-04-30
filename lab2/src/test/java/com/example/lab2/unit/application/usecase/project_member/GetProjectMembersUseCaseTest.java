package com.example.lab2.unit.application.usecase.project_member;

import com.example.lab2.application.usecase.project_member.GetProjectMembersUseCase;
import com.example.lab2.domain.enums.ProjectMemberRole;
import com.example.lab2.domain.model.ProjectMember;
import com.example.lab2.domain.repository.ProjectMemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetProjectMembersUseCaseTest {

    @Mock
    private ProjectMemberRepository repository;

    @InjectMocks
    private GetProjectMembersUseCase useCase;

    @Test
    @SuppressWarnings("unchecked")
    void execute_ShouldReturnProjectMembers() {
        Long projectId = 1L;
        Long userId = 2L;
        ProjectMemberRole role = ProjectMemberRole.CONTRIBUTOR;
        Pageable pageable = mock(Pageable.class);
        Page<ProjectMember> expectedPage = mock(Page.class);
        when(repository.search(projectId, userId, role, pageable)).thenReturn(expectedPage);

        Page<ProjectMember> result = useCase.execute(projectId, userId, role, pageable);

        assertEquals(expectedPage, result);
        verify(repository).search(projectId, userId, role, pageable);
    }
}
