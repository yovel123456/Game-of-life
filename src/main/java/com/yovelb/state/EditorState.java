package com.yovelb.state;

import com.yovelb.model.Board;
import com.yovelb.model.CellPosition;
import com.yovelb.model.CellState;
import com.yovelb.util.Property;

public class EditorState {
    private final Property<CellState> drawModeProperty = new Property<>(CellState.ALIVE);
    private final Property<CellPosition> cursorPositionProperty = new Property<>();
    private final Property<Board> editorBoard = new Property<>();

    public EditorState(Board initialBoard) {
        this.editorBoard.set(initialBoard);
    }

    public Property<CellState> getDrawModeProperty() {
        return drawModeProperty;
    }

    public Property<CellPosition> getCursorPositionProperty() {
        return cursorPositionProperty;
    }

    public Property<Board> getBoardProperty() {
        return editorBoard;
    }
}
