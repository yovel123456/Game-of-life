package com.yovelb.gol.logic.editor;

import com.yovelb.gol.model.Board;
import com.yovelb.gol.model.CellPosition;
import com.yovelb.gol.model.CellState;
import com.yovelb.gol.state.EditorState;

public class BoardEditCommand implements EditorCommand{
    private final CellPosition cursorPosition;
    private final CellState drawMode;

    public BoardEditCommand(CellPosition cursorPosition, CellState drawMode) {
        this.cursorPosition = cursorPosition;
        this.drawMode = drawMode;
    }

    @Override
    public void execute(EditorState editorState) {
        Board board = editorState.getBoardProperty().get();
        board.setState(cursorPosition.getX(), cursorPosition.getY(), drawMode);
        editorState.getBoardProperty().set(board);
    }
}
