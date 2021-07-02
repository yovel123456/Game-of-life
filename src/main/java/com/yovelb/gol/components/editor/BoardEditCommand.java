package com.yovelb.gol.components.editor;

import com.yovelb.gol.model.Board;

public class BoardEditCommand implements UndoableEditorCommand {
    private final Edit edit;

    public BoardEditCommand(Edit edit) {
        this.edit = new Edit(edit);
    }

    @Override
    public void execute(EditorState editorState) {
        Board board = editorState.getBoardProperty().get();
        for (Change change : edit) {
            board.setState(change.getCellPosition().getX(), change.getCellPosition().getY(), change.getNextState());
        }
        editorState.getBoardProperty().set(board);
    }

    @Override
    public void undo(EditorState editorState) {
        Board board = editorState.getBoardProperty().get();
        for (Change change : edit) {
            board.setState(change.getCellPosition().getX(), change.getCellPosition().getY(), change.getPrevState());
        }
        editorState.getBoardProperty().set(board);
    }
}