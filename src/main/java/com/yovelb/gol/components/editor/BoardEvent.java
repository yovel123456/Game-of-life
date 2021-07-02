package com.yovelb.gol.components.editor;

import com.yovelb.gol.model.CellPosition;
import com.yovelb.app.event.Event;

public class BoardEvent implements Event {

    public enum Type {
        CURSOR_MOVED,
        PRESSED,
        RELEASED
    }

    private final Type eventType;
    private final CellPosition cursorPosition;

    public BoardEvent(Type eventType, CellPosition cursorPosition) {
        this.eventType = eventType;
        this.cursorPosition = cursorPosition;
    }

    public Type getEventType() {
        return eventType;
    }

    public CellPosition getCursorPosition() {
        return cursorPosition;
    }
}
