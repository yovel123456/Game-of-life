package com.yovelb.util.event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventBusTest {
    private EventBus eventBus;

    @BeforeEach
    void setUp() {
        eventBus = new EventBus();
    }

    @Test
    void singleListenerCalled() {
        HelloEvent event = new HelloEvent();
        TestEventListener<HelloEvent> listener = new TestEventListener<>();
        eventBus.listenFor(HelloEvent.class, listener);

        eventBus.emit(event);

        assertTrue(listener.listenerCalled);
    }
    @Test
    void MultipleListenersCalled() {
        HelloEvent event = new HelloEvent();
        TestEventListener<HelloEvent> listener1 = new TestEventListener<>();
        TestEventListener<HelloEvent> listener2 = new TestEventListener<>();
        eventBus.listenFor(HelloEvent.class, listener1);
        eventBus.listenFor(HelloEvent.class, listener2);

        eventBus.emit(event);

        assertTrue(listener1.listenerCalled);
        assertTrue(listener2.listenerCalled);
    }
    @Test
    void MultipleEventTypes() {
        WorldEvent event2 = new WorldEvent();
        TestEventListener<HelloEvent> listener1 = new TestEventListener<>();
        TestEventListener<WorldEvent> listener2 = new TestEventListener<>();
        eventBus.listenFor(HelloEvent.class, listener1);
        eventBus.listenFor(WorldEvent.class, listener2);

        eventBus.emit(event2);

        assertFalse(listener1.listenerCalled);
        assertTrue(listener2.listenerCalled);
    }

    private static class TestEventListener<T extends Event> implements EventListener<T> {
        private boolean listenerCalled;

        @Override
        public void handle(T event) {
            listenerCalled = true;
        }
    }

    private static class HelloEvent implements Event {
        private final String value = "Hello";
    }
    private static class WorldEvent implements Event {
        private final String value = "World";
    }

}