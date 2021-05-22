package com.yovelb.gol.state;

import com.yovelb.gol.model.Board;
import com.yovelb.gol.model.CellPosition;
import com.yovelb.gol.model.CellState;
import com.yovelb.app.observable.Property;

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
