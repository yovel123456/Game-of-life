package com.yovelb.logic;

import com.yovelb.model.Board;
import com.yovelb.model.CellPosition;
import com.yovelb.model.CellState;
import com.yovelb.state.EditorState;

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
