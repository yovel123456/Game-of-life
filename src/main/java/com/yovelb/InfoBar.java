package com.yovelb;

import com.yovelb.model.CellState;
import com.yovelb.viewmodel.EditorViewModel;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class InfoBar extends HBox {
    private final Label cursor;
    private final Label editingTool;

    public InfoBar(EditorViewModel editorViewModel) {
        editorViewModel.listenToDrawMode(this::setDrawMode);

        this.cursor = new Label("Cursor: (0, 0)");
        this.editingTool = new Label("Draw Mode: Drawing");

        Pane spacer = new Pane();
        spacer.setMinSize(0, 0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        getChildren().addAll(cursor, editingTool);
    }
    private void setDrawMode(CellState drawMode) {
        String drawModeFormat = "Draw Mode: %s";
        editingTool.setText(String.format(drawModeFormat, (drawMode == CellState.ALIVE) ? "Drawing" : "Erasing"));
    }

    public void setCursorPosition(int x, int y) {
        String cursorPositionFormat = "Cursor: (%d, %d)";
        cursor.setText(String.format(cursorPositionFormat, x, y));
    }
}