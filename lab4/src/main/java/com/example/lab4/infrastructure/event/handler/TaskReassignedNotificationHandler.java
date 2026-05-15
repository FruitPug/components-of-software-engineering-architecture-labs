package com.example.lab4.infrastructure.event.handler;

import com.example.lab4.domain.event.EventBus;
import com.example.lab4.domain.event.TaskReassignedEvent;
import com.example.lab4.domain.service.notification.NotificationService;
import com.example.lab4.domain.service.notification.TaskReassignedNotification;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskReassignedNotificationHandler {

    private final EventBus eventBus;
    private final NotificationService notificationService;

    @PostConstruct
    public void subscribe() {
        eventBus.subscribe(
            TaskReassignedEvent.class,
            this::handle
        );
    }

    private void handle(TaskReassignedEvent event) {
        try {
            notificationService.sendTaskReassignedNotification(
                new TaskReassignedNotification(
                    event.taskId(),
                    event.taskTitle(),
                    event.oldAssigneeId(),
                    event.newAssigneeId()
                )
            );
        } catch (Exception e) {
            log.error(
                "Failed to process TaskReassignedEvent for task {}",
                event.taskId(),
                e
            );
        }
    }
}
