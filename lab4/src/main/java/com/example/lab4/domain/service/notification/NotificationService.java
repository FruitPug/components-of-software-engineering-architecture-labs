package com.example.lab4.domain.service.notification;

public interface NotificationService {

    void sendTaskCreatedNotification(TaskCreatedNotification notification);

    void sendTaskReassignedNotification(TaskReassignedNotification notification);
}
