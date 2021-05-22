package com.yovelb.gol.logic.editor;

import com.yovelb.gol.model.CellPosition;
import com.yovelb.app.event.Event;

public class BoardEvent implements Event {

    public enum Type {
        CURSOR_MOVED,
        CURSOR_PRESSED
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
