package com.yovelb.gol.viewmodel;

import com.yovelb.gol.model.CellPosition;
import com.yovelb.gol.model.CellState;
import com.yovelb.app.observable.Property;

public class InfoBarViewModel {
    private final Property<CellState> currentDrawModeProperty = new Property<>(CellState.ALIVE);
    private final Property<CellPosition> cursorPositionProperty = new Property<>();

    public Property<CellState> getCurrentDrawModeProperty() {
        return currentDrawModeProperty;
    }

    public Property<CellPosition> getCursorPositionProperty() {
        return cursorPositionProperty;
    }
}
