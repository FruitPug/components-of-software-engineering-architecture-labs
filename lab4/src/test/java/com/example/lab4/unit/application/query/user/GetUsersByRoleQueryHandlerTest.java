package com.example.lab4.unit.application.query.user;

import com.example.lab4.application.query.user.GetUsersByRoleQuery;
import com.example.lab4.application.query.user.GetUsersByRoleQueryHandler;
import com.example.lab4.application.query.user.UserReadModel;
import com.example.lab4.application.query.user.UserReadRepository;
import com.example.lab4.domain.enums.UserRole;
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
class GetUsersByRoleQueryHandlerTest {

    @Mock
    private UserReadRepository repository;

    @InjectMocks
    private GetUsersByRoleQueryHandler queryHandler;

    @Test
    @SuppressWarnings("unchecked")
    void handle_ShouldReturnUsersByRole() {
        UserRole role = UserRole.DEVELOPER;
        Pageable pageable = mock(Pageable.class);
        GetUsersByRoleQuery query = new GetUsersByRoleQuery(role, pageable);
        Page<UserReadModel> expectedPage = mock(Page.class);

        when(repository.findByRole(role, pageable)).thenReturn(expectedPage);

        Page<UserReadModel> result = queryHandler.handle(query);

        assertEquals(expectedPage, result);
        verify(repository).findByRole(role, pageable);
    }
}
