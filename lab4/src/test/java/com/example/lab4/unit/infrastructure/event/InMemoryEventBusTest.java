package com.example.lab4.unit.infrastructure.event;

import com.example.lab4.infrastructure.event.InMemoryEventBus;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryEventBusTest {

    @Test
    void shouldPublishEventToSubscribers() {
        InMemoryEventBus eventBus = new InMemoryEventBus();
        List<String> receivedEvents = new ArrayList<>();

        eventBus.subscribe(String.class, receivedEvents::add);
        eventBus.publish("Event 1");
        eventBus.publish("Event 2");

        assertEquals(2, receivedEvents.size());
        assertEquals("Event 1", receivedEvents.get(0));
        assertEquals("Event 2", receivedEvents.get(1));
    }

    @Test
    void shouldNotPublishEventToSubscribersOfDifferentType() {
        InMemoryEventBus eventBus = new InMemoryEventBus();
        List<String> receivedEvents = new ArrayList<>();

        eventBus.subscribe(String.class, receivedEvents::add);
        eventBus.publish(123L);

        assertEquals(0, receivedEvents.size());
    }

    @Test
    void shouldHandleMultipleSubscribersForSameType() {
        InMemoryEventBus eventBus = new InMemoryEventBus();
        List<String> received1 = new ArrayList<>();
        List<String> received2 = new ArrayList<>();

        eventBus.subscribe(String.class, received1::add);
        eventBus.subscribe(String.class, received2::add);

        eventBus.publish("Hello");

        assertEquals(1, received1.size());
        assertEquals("Hello", received1.get(0));
        assertEquals(1, received2.size());
        assertEquals("Hello", received2.get(0));
    }
}
