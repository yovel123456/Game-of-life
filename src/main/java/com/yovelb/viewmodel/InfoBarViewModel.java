package com.yovelb.viewmodel;

import com.yovelb.model.CellPosition;
import com.yovelb.model.CellState;
import com.yovelb.util.Property;

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
