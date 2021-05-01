package com.yovelb.logic.editor;

import com.yovelb.model.CellState;
import com.yovelb.state.EditorState;

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
