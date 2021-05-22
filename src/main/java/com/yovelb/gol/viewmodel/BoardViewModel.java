package com.yovelb.gol.viewmodel;

import com.yovelb.gol.model.Board;
import com.yovelb.gol.model.CellPosition;
import com.yovelb.app.observable.Property;

public class BoardViewModel {
    private final Property<Board> boardProperty = new Property<>();
    private final Property<CellPosition> cursorPositionProperty = new Property<>();

    public Property<Board> getBoardProperty() {
        return boardProperty;
    }

    public Property<CellPosition> getCursorPositionProperty() {
        return cursorPositionProperty;
    }
}