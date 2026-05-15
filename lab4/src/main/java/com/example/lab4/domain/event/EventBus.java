package com.example.lab4.domain.event;

import java.util.function.Consumer;

public interface EventBus {

    <T> void subscribe(Class<T> eventType, Consumer<T> handler);

    void publish(Object event);
}
