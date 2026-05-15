package com.example.lab4.domain.service.notification;

public record TaskCreatedNotification(
        Long id,
        String title,
        Long projectId,
        Long creatorUserId,
        Long assigneeUserId
) {
}
