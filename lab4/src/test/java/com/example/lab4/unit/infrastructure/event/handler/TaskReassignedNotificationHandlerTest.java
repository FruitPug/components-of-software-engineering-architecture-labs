package com.example.lab4.unit.infrastructure.event.handler;

import com.example.lab4.domain.event.EventBus;
import com.example.lab4.domain.event.TaskReassignedEvent;
import com.example.lab4.domain.service.notification.NotificationService;
import com.example.lab4.domain.service.notification.TaskReassignedNotification;
import com.example.lab4.infrastructure.event.handler.TaskReassignedNotificationHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TaskReassignedNotificationHandlerTest {

    @Mock
    private EventBus eventBus;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private TaskReassignedNotificationHandler handler;

    @Test
    @SuppressWarnings("unchecked")
    void shouldSubscribeToEventBusOnSubscribe() {
        handler.subscribe();

        verify(eventBus).subscribe(eq(TaskReassignedEvent.class), any(Consumer.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldSendNotificationWhenEventIsPublished() {
        // Given
        ArgumentCaptor<Consumer<TaskReassignedEvent>> consumerCaptor = ArgumentCaptor.forClass(Consumer.class);
        handler.subscribe();
        verify(eventBus).subscribe(eq(TaskReassignedEvent.class), consumerCaptor.capture());
        Consumer<TaskReassignedEvent> capturedHandler = consumerCaptor.getValue();

        TaskReassignedEvent event = new TaskReassignedEvent(
                1L, "Task Title", 2L, 3L, Instant.now()
        );

        // When
        capturedHandler.accept(event);

        // Then
        ArgumentCaptor<TaskReassignedNotification> notificationCaptor = ArgumentCaptor.forClass(TaskReassignedNotification.class);
        verify(notificationService).sendTaskReassignedNotification(notificationCaptor.capture());
        
        TaskReassignedNotification notification = notificationCaptor.getValue();
        assertEquals(event.taskId(), notification.taskId());
        assertEquals(event.taskTitle(), notification.taskTitle());
        assertEquals(event.oldAssigneeId(), notification.oldAssigneeId());
        assertEquals(event.newAssigneeId(), notification.newAssigneeId());
    }
}
