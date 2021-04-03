package com.yovelb.logic;

import com.yovelb.util.event.Event;

public class SimulatorEvent implements Event {
    public enum Type {
        START,
        STOP,
        STEP,
        RESET
    }

    private final Type eventType;

    public SimulatorEvent(Type eventType) {
        this.eventType = eventType;
    }

    public Type getEventType() {
        return eventType;
    }
}
