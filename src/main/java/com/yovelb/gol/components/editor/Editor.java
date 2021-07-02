package com.yovelb.gol.components.editor;

import com.yovelb.app.command.CommandExecutor;
import com.yovelb.gol.components.simulator.SimulatorEvent;
import com.yovelb.gol.model.CellPosition;
import com.yovelb.gol.model.CellState;

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
        cursorPositionChanged(boardEvent.getCursorPosition());
        switch (boardEvent.getEventType()) {
            case PRESSED:
                beginEdit();
                handleEdit(boardEvent.getCursorPosition());
                break;
            case CURSOR_MOVED:
                if (this.editorState.getEditInProgress().get()) {
                    handleEdit(boardEvent.getCursorPosition());
                }
                break;
            case RELEASED:
                handleEdit(boardEvent.getCursorPosition());
                endEdit();
                break;
        }
    }

    private void beginEdit() {
        this.editorState.getEditInProgress().set(true);
        this.editorState.getCurrentEdit().set(new Edit());
    }

    private void endEdit() {
        BoardEditCommand editCommand = new BoardEditCommand(this.editorState.getCurrentEdit().get());
        this.commandExecutor.execute(editCommand);
        this.editorState.getEditInProgress().set(false);
        this.editorState.getCurrentEdit().set(null);
    }

    public void handleSimulatorEvent(SimulatorEvent event) {
        if (event.getEventType() == SimulatorEvent.Type.RESET) {
            this.drawingEnabled = true;
        } else if (event.getEventType() == SimulatorEvent.Type.START || event.getEventType() == SimulatorEvent.Type.STEP) {
            this.drawingEnabled = false;
        }
    }

    private void handleEdit(CellPosition cursorPosition) {
        if (this.drawingEnabled) {
            CellState currentState = editorState.getBoardProperty().get().getState(cursorPosition.getX(), cursorPosition.getY());
            CellState newState = this.editorState.getDrawModeProperty().get();
            Change change = new Change(cursorPosition, newState, currentState);
            this.editorState.getCurrentEdit().get().add(change);
        }
    }

    private void cursorPositionChanged(CellPosition cursorPosition) {
        EditorCommand command = (state) -> state.getCursorPositionProperty().set(cursorPosition);
        this.commandExecutor.execute(command);
    }
}