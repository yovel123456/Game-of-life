package com.yovelb.viewmodel;

import com.yovelb.model.CellState;
import com.yovelb.util.event.Event;

public class DrawModeEvent implements Event {

    private final CellState newDrawMode;

    public DrawModeEvent(CellState newDrawMode) {
        this.newDrawMode = newDrawMode;
    }

    public CellState getNewDrawMode() {
        return newDrawMode;
    }
}
