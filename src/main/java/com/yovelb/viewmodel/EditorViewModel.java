package com.yovelb.viewmodel;

import com.yovelb.model.Board;
import com.yovelb.model.CellState;
import com.yovelb.util.Property;

import java.util.LinkedList;
import java.util.List;

public class EditorViewModel {
    private final Property<CellState> drawModeProperty = new Property<>(CellState.ALIVE);

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

    public void boardPress(int simX, int simY) {
        if (drawingEnabled) {
            this.editorBoard.setState(simX, simY, this.drawModeProperty.get());
            this.boardViewModel.getBoardProperty().set(this.editorBoard);
        }
    }

    public Property<CellState> getDrawModeProperty() {
        return drawModeProperty;
    }
}