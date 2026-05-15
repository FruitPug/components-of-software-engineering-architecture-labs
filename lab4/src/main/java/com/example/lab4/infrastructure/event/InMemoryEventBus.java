package com.example.lab4.infrastructure.event;

import com.example.lab4.domain.event.EventBus;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Consumer;

@Component
public class InMemoryEventBus implements EventBus {

    private final Map<Class<?>, List<Consumer<Object>>> handlers =
            new HashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <T> void subscribe(Class<T> eventType, Consumer<T> handler) {
        handlers.computeIfAbsent(
            eventType,
            k -> new ArrayList<>()
        ).add((Consumer<Object>) handler);
    }

    @Override
    public void publish(Object event) {
        handlers.getOrDefault(
            event.getClass(),
            Collections.emptyList()
        ).forEach(handler -> handler.accept(event));
    }
}
