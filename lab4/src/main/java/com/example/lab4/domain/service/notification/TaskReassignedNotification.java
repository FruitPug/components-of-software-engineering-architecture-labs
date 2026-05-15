package com.example.lab4.domain.service.notification;

public record TaskReassignedNotification(
        Long taskId,
        String taskTitle,
        Long oldAssigneeId,
        Long newAssigneeId
) {
}
