package com.example.lab4.unit.infrastructure.service.notification;

import com.example.lab4.domain.service.notification.TaskCreatedNotification;
import com.example.lab4.domain.service.notification.TaskReassignedNotification;
import com.example.lab4.infrastructure.service.notification.ConsoleNotificationService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ConsoleNotificationServiceTest {

    private final ConsoleNotificationService service = new ConsoleNotificationService();

    @Test
    void sendTaskCreatedNotification_ShouldNotThrowException() {
        TaskCreatedNotification notification = new TaskCreatedNotification(
                1L, "Test Task", 10L, 2L, 3L
        );

        assertDoesNotThrow(() -> service.sendTaskCreatedNotification(notification));
    }

    @Test
    void sendTaskReassignedNotification_ShouldNotThrowException() {
        TaskReassignedNotification notification = new TaskReassignedNotification(
                1L, "Test Task", 3L, 4L
        );

        assertDoesNotThrow(() -> service.sendTaskReassignedNotification(notification));
    }
}
