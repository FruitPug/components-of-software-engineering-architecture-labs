package com.example.lab4.unit.domain.model;

import com.example.lab4.domain.enums.ProjectMemberRole;
import com.example.lab4.domain.error.DomainError;
import com.example.lab4.domain.model.ProjectMember;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProjectMemberTest {

    @Test
    void shouldCreateProjectMemberSuccessfully() {
        ProjectMember member = new ProjectMember(1L, 2L, ProjectMemberRole.CONTRIBUTOR);

        assertEquals(1L, member.getProjectId());
        assertEquals(2L, member.getUserId());
        assertEquals(ProjectMemberRole.CONTRIBUTOR, member.getRole());
        assertNotNull(member.getJoinedAt());
    }

    @Test
    void shouldThrowExceptionWhenProjectIdIsNull() {
        DomainError exception = assertThrows(DomainError.class, () -> 
            new ProjectMember(null, 2L, ProjectMemberRole.CONTRIBUTOR)
        );
        assertEquals("INVALID_MEMBER_REFERENCE", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenUserIdIsNull() {
        DomainError exception = assertThrows(DomainError.class, () -> 
            new ProjectMember(1L, null, ProjectMemberRole.CONTRIBUTOR)
        );
        assertEquals("INVALID_MEMBER_REFERENCE", exception.getMessage());
    }

    @Test
    void shouldCreateWithAllFields() {
        LocalDateTime now = LocalDateTime.now();
        ProjectMember member = new ProjectMember(10L, 1L, 2L, ProjectMemberRole.OWNER, now);

        assertEquals(10L, member.getId());
        assertEquals(1L, member.getProjectId());
        assertEquals(2L, member.getUserId());
        assertEquals(ProjectMemberRole.OWNER, member.getRole());
        assertEquals(now, member.getJoinedAt());
    }
}
