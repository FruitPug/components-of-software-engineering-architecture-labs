package com.example.lab4.infrastructure.service.notification;

import com.example.lab4.domain.service.notification.NotificationService;
import com.example.lab4.domain.service.notification.TaskCreatedNotification;
import com.example.lab4.domain.service.notification.TaskReassignedNotification;
import org.springframework.stereotype.Service;

@Service
public class ConsoleNotificationService implements NotificationService {

    @Override
    public void sendTaskCreatedNotification(TaskCreatedNotification notification) {
        System.out.printf(
            """
            =========================
            TASK CREATED NOTIFICATION
            =========================
            Task ID: %s
            Title: %s
            Project ID: %s
            Creator ID: %s
            Assignee ID: %s
            %n""",
                notification.id(),
                notification.title(),
                notification.projectId(),
                notification.creatorUserId(),
                notification.assigneeUserId()
        );
    }

    @Override
    public void sendTaskReassignedNotification(TaskReassignedNotification notification) {
        System.out.printf(
            """
            =========================
            TASK REASSIGNED
            =========================
            Task ID: %s
            Title: %s
            Old assignee: %s
            New assignee: %s
            %n""",
                notification.taskId(),
                notification.taskTitle(),
                notification.oldAssigneeId(),
                notification.newAssigneeId()
        );
    }
}
