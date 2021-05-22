package com.yovelb.app.event;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EventBus {
    private final Map<Class<? extends Event>, List<EventListener>> listeners = new HashMap<>();

    public void emit(Event event) {
        Class<? extends Event> eventClass = event.getClass();
        List<EventListener> eventListeners = listeners.get(eventClass);
        eventListeners.forEach(eventListener -> eventListener.handle(event));
    }

    public <T extends Event> void listenFor(Class<T> eventClass, EventListener<T> listener) {
        if (!listeners.containsKey(eventClass)) {
            listeners.put(eventClass, new LinkedList<>());
        }
        listeners.get(eventClass).add(listener);
    }
}