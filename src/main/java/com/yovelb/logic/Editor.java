package com.yovelb.logic;

import com.yovelb.model.Board;
import com.yovelb.model.CellPosition;
import com.yovelb.model.CellState;
import com.yovelb.util.Property;

public class Editor {
    private final Property<CellState> drawModeProperty = new Property<>(CellState.ALIVE);
    private final Property<CellPosition> cursorPositionProperty = new Property<>();
    private final Property<Board> editorBoard = new Property<>();

    private boolean drawingEnabled = true;

    public Editor(Board initialBoard) {
        this.editorBoard.set(initialBoard);
    }

    public void handle(DrawModeEvent drawModeEvent) {
        this.drawModeProperty.set(drawModeEvent.getNewDrawMode());
    }

    public void handle(BoardEvent boardEvent) {
        switch (boardEvent.getEventType()) {
            case CURSOR_MOVED:
                this.cursorPositionProperty.set(boardEvent.getCursorPosition());
                break;
            case CURSOR_PRESSED:
                boardPress(boardEvent.getCursorPosition());
                break;
        }
    }

    public void onAppStateChanged(ApplicationState state) {
        if (state == ApplicationState.EDITING) {
            drawingEnabled = true;
            this.editorBoard.set(this.editorBoard.get());
        } else {
            drawingEnabled = false;
        }
    }

    private void boardPress(CellPosition cursorPosition) {
        this.cursorPositionProperty.set(cursorPosition);
        if (this.drawingEnabled) {
            Board board = this.editorBoard.get();
            board.setState(cursorPosition.getX(), cursorPosition.getY(), this.drawModeProperty.get());
            this.editorBoard.set(board);
        }
    }

    public Property<CellState> getDrawModeProperty() {
        return drawModeProperty;
    }

    public Property<CellPosition> getCursorPositionProperty() {
        return cursorPositionProperty;
    }

    public Property<Board> getBoard() {
        return editorBoard;
    }
}