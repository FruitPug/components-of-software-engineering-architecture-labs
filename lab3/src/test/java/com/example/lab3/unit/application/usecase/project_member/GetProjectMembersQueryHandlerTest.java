package com.example.lab3.unit.application.usecase.project_member;

import com.example.lab3.application.query.project_member.GetProjectMembersQuery;
import com.example.lab3.application.query.project_member.GetProjectMembersQueryHandler;
import com.example.lab3.application.query.project_member.ProjectMemberReadModel;
import com.example.lab3.application.query.project_member.ProjectMemberReadRepository;
import com.example.lab3.domain.enums.ProjectMemberRole;
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
class GetProjectMembersQueryHandlerTest {

    @Mock
    private ProjectMemberReadRepository repository;

    @InjectMocks
    private GetProjectMembersQueryHandler useCase;

    @Test
    @SuppressWarnings("unchecked")
    void handle_ShouldReturnProjectMembers() {
        Long projectId = 1L;
        Long userId = 2L;
        ProjectMemberRole role = ProjectMemberRole.CONTRIBUTOR;
        Pageable pageable = mock(Pageable.class);
        Page<ProjectMemberReadModel> expectedPage = mock(Page.class);
        when(repository.search(projectId, userId, role, pageable)).thenReturn(expectedPage);

        Page<ProjectMemberReadModel> result = useCase.handle(new GetProjectMembersQuery(projectId, userId, role, pageable));

        assertEquals(expectedPage, result);
        verify(repository).search(projectId, userId, role, pageable);
    }
}
