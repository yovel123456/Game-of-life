package com.yovelb.gol.logic.editor;

import com.yovelb.gol.model.CellState;
import com.yovelb.app.event.Event;

public class DrawModeEvent implements Event {

    private final CellState newDrawMode;

    public DrawModeEvent(CellState newDrawMode) {
        this.newDrawMode = newDrawMode;
    }

    public CellState getNewDrawMode() {
        return newDrawMode;
    }
}
