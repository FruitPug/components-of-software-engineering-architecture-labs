package com.example.lab4.domain.event;

import java.time.Instant;

public record TaskReassignedEvent(
        Long taskId,
        String taskTitle,
        Long oldAssigneeId,
        Long newAssigneeId,
        Instant occurredAt
) {
}
