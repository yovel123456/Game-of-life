package com.yovelb.gol.components.simulator;

import com.yovelb.app.event.Event;

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
