package com.yovelb.gol.components.editor;

import com.yovelb.gol.model.Board;
import com.yovelb.gol.model.CellPosition;
import com.yovelb.gol.model.CellState;
import com.yovelb.gol.view.DrawLayerImplementation;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CurrentEditDrawLayer extends DrawLayerImplementation {

    private final EditorState editorState;

    public CurrentEditDrawLayer(EditorState editorState) {
        this.editorState = editorState;
        this.editorState.getCurrentEdit().listen(e -> this.invalidate());
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (!editorState.getCurrentEdit().isPresent()) { return; }

        Edit edit = this.editorState.getCurrentEdit().get();

        for (Change change : edit) {
            if (change.getNextState() == CellState.ALIVE) {
                gc.setFill(Color.BLACK);
            } else {
                gc.setFill(Color.LIGHTGRAY);
            }
            CellPosition cellPosition = change.getCellPosition();
            gc.fillRect(cellPosition.getX(), cellPosition.getY(), 1, 1);
        }
    }

    @Override
    public int getLayer() {
        return 1;
    }
}