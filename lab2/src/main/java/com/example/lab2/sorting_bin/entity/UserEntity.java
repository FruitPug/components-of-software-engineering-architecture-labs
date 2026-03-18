package com.example.lab2.sorting_bin.entity;

import com.example.lab2.sorting_bin.entity.enums.UserRole;
import com.example.lab2.sorting_bin.entity.interfaces.SoftDeletable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@SQLRestriction("is_deleted = false")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements SoftDeletable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "user_role_enum", nullable = false)
    private UserRole role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    @OneToMany(mappedBy = "user")
    private Set<ProjectMemberEntity> projectMemberships;

    @OneToMany(mappedBy = "creator")
    private List<TaskEntity> createdTasks;

    @OneToMany(mappedBy = "assignee")
    private List<TaskEntity> assignedTasks;

    @OneToMany(mappedBy = "author")
    private List<TaskCommentEntity> comments;
}
