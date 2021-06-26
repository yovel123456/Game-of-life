package com.yovelb.gol.components.editor;

import com.yovelb.app.command.CommandExecutor;
import com.yovelb.gol.components.simulator.SimulatorEvent;
import com.yovelb.gol.model.CellPosition;
import com.yovelb.gol.model.CellState;
import com.yovelb.gol.state.EditorState;

public class Editor {
    private final EditorState editorState;

    private boolean drawingEnabled = true;
    private final CommandExecutor commandExecutor;

    public Editor(EditorState editorState, CommandExecutor commandExecutor) {
        this.editorState = editorState;
        this.commandExecutor = commandExecutor;
    }

    public void handle(DrawModeEvent drawModeEvent) {
        DrawModeCommand command = new DrawModeCommand(drawModeEvent.getNewDrawMode());
        this.commandExecutor.execute(command);
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

    public void handleSimulatorEvent(SimulatorEvent event) {
        if (event.getEventType() == SimulatorEvent.Type.RESET) {
            drawingEnabled = true;
        } else if (event.getEventType() == SimulatorEvent.Type.START || event.getEventType() == SimulatorEvent.Type.STEP) {
            drawingEnabled = false;
        }
    }

    private void boardPress(CellPosition cursorPosition) {
        cursorPositionChanged(cursorPosition);
        if (this.drawingEnabled) {
            CellState currentState = editorState.getBoardProperty().get().getState(cursorPosition.getX(), cursorPosition.getY());
            CellState newState = this.editorState.getDrawModeProperty().get();

            if (currentState != newState) {
                BoardEditCommand command = new BoardEditCommand(cursorPosition, newState, currentState);
                this.commandExecutor.execute(command);
            }
        }
    }

    private void cursorPositionChanged(CellPosition cursorPosition) {
        EditorCommand command = (state) -> state.getCursorPositionProperty().set(cursorPosition);
        this.commandExecutor.execute(command);
    }
}