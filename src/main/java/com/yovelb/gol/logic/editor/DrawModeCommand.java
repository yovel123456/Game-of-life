package com.yovelb.gol.logic.editor;

import com.yovelb.gol.model.CellState;
import com.yovelb.gol.state.EditorState;

public class DrawModeCommand implements EditorCommand{
    private final CellState newDrawMode;

    public DrawModeCommand(CellState newDrawMode) {
        this.newDrawMode = newDrawMode;
    }

    @Override
    public void execute(EditorState editorState) {
        editorState.getDrawModeProperty().set(newDrawMode);
    }
}
