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

    public void onAppStateChanged(ApplicationState state) {
        if (state == ApplicationState.EDITING) {
            drawingEnabled = true;
            this.boardViewModel.getBoardProperty().set(editorBoard);
        } else {
            drawingEnabled = false;
        }
    }

    public void boardPress(CellPosition cursorPosition) {
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
}