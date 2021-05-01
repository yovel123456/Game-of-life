package com.yovelb.logic.editor;

import com.yovelb.logic.ApplicationState;
import com.yovelb.model.CellPosition;
import com.yovelb.state.EditorState;

public class Editor {
    private final EditorState editorState;

    private boolean drawingEnabled = true;

    public Editor(EditorState editorState) {
        this.editorState = editorState;
    }

    public void handle(DrawModeEvent drawModeEvent) {
        DrawModeCommand command = new DrawModeCommand(drawModeEvent.getNewDrawMode());
        command.execute(this.editorState);
    }

    public void handle(BoardEvent boardEvent) {
        switch (boardEvent.getEventType()) {
            case CURSOR_MOVED:
                cursorPositionChanged(boardEvent.getCursorPosition());
                break;
            case CURSOR_PRESSED:
                boardPress(boardEvent.getCursorPosition());
                break;
        }
    }

    public void onAppStateChanged(ApplicationState state) {
        drawingEnabled = state == ApplicationState.EDITING;
    }

    private void boardPress(CellPosition cursorPosition) {
        cursorPositionChanged(cursorPosition);
        if (this.drawingEnabled) {
            BoardEditCommand command = new BoardEditCommand(cursorPosition, this.editorState.getDrawModeProperty().get());
            command.execute(this.editorState);
        }
    }

    private void cursorPositionChanged(CellPosition cursorPosition) {
        EditorCommand command = (state) -> state.getCursorPositionProperty().set(cursorPosition);
        command.execute(this.editorState);
    }
}