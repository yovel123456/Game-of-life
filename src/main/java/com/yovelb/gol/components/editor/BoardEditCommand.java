package com.yovelb.gol.components.editor;

import com.yovelb.gol.model.Board;
import com.yovelb.gol.model.CellPosition;
import com.yovelb.gol.model.CellState;
import com.yovelb.gol.state.EditorState;

public class BoardEditCommand implements UndoableEditorCommand {
    private final CellPosition cursorPosition;
    private final CellState drawMode;
    private CellState prevState;

    public BoardEditCommand(CellPosition cursorPosition, CellState drawMode, CellState prevState) {
        this.cursorPosition = cursorPosition;
        this.drawMode = drawMode;
        this.prevState = prevState;
    }

    @Override
    public void execute(EditorState editorState) {
        Board board = editorState.getBoardProperty().get();
        board.setState(cursorPosition.getX(), cursorPosition.getY(), drawMode);
        editorState.getBoardProperty().set(board);
    }

    @Override
    public void undo(EditorState editorState) {
        Board board = editorState.getBoardProperty().get();
        board.setState(cursorPosition.getX(), cursorPosition.getY(), prevState);
        editorState.getBoardProperty().set(board);
    }
}