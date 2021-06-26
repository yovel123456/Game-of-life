package com.yovelb.gol.components.editor;

import com.yovelb.gol.model.CellPosition;
import com.yovelb.gol.state.EditorState;
import com.yovelb.gol.view.DrawLayerImplementation;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ToolDrawLayer extends DrawLayerImplementation {

    private EditorState editorState;

    public ToolDrawLayer(EditorState editorState) {
        this.editorState = editorState;
        this.editorState.getCursorPositionProperty().listen(cp -> this.invalidate());
    }

    @Override
    public void draw(GraphicsContext gc) {
        CellPosition cursor = this.editorState.getCursorPositionProperty().get();
        if (cursor != null) {
            gc.setFill(new Color(0.3, 0.3, 0.3, 0.5));
            gc.fillRect(cursor.getX(), cursor.getY(), 1, 1);
        }
    }

    @Override
    public int getLayer() {
        return 9;
    }
}