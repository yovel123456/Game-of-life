package com.yovelb.viewmodel;

import com.yovelb.model.Board;
import com.yovelb.model.CellPosition;
import com.yovelb.util.Property;

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