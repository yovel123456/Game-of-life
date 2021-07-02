package com.yovelb.gol.components.editor;

import com.yovelb.gol.model.CellState;

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
