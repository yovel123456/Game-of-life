package com.yovelb.viewmodel;

import com.yovelb.model.Board;
import com.yovelb.model.CellPosition;
import com.yovelb.model.CellState;
import com.yovelb.util.Property;

public class EditorViewModel {
    private final Property<CellState> drawModeProperty = new Property<>(CellState.ALIVE);
    private final Property<CellPosition> cursorPositionProperty = new Property<>();

    private final BoardViewModel boardViewModel;
    private final Board editorBoard;
    private boolean drawingEnabled = true;

    public EditorViewModel(BoardViewModel boardViewModel, Board initialBoard) {
        this.boardViewModel = boardViewModel;
        this.editorBoard = initialBoard;
    }

    public void handle(DrawModeEvent drawModeEvent) {
        this.drawModeProperty.set(drawModeEvent.getNewDrawMode());
    }

    public void handle(BoardEvent boardEvent) {
        switch (boardEvent.getEventType()) {
            case CURSOR_MOVED:
                cursorPositionProperty.set(boardEvent.getCursorPosition());
                break;
            case CURSOR_PRESSED:
                boardPress(boardEvent.getCursorPosition());
                break;
        }
    }

    public void onAppStateChanged(ApplicationState state) {
        if (state == ApplicationState.EDITING) {
            drawingEnabled = true;
            this.boardViewModel.getBoardProperty().set(editorBoard);
        } else {
            drawingEnabled = false;
        }
    }

    private void boardPress(CellPosition cursorPosition) {
        this.cursorPositionProperty.set(cursorPosition);
        if (drawingEnabled) {
            this.editorBoard.setState(cursorPosition.getX(), cursorPosition.getY(), this.drawModeProperty.get());
            this.boardViewModel.getBoardProperty().set(this.editorBoard);
        }
    }

    public Property<CellState> getDrawModeProperty() {
        return drawModeProperty;
    }

    public Property<CellPosition> getCursorPositionProperty() {
        return cursorPositionProperty;
    }

    public Board getBoard() {
        return editorBoard;
    }
}