package com.yovelb;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class InfoBar extends HBox {
    private static String drawModeFormat = "Draw Mode: %s";
    private static String cursorPositionFormat = "Cursor: (%d, %d)";

    private Label cursor;
    private Label editingTool;

    public InfoBar() {
        this.cursor = new Label("Cursor: (0, 0)");
        this.editingTool = new Label("Draw Mode: Drawing");

        Pane spacer = new Pane();
        spacer.setMinSize(0, 0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        getChildren().addAll(cursor, editingTool);
    }
    public void setDrawMode(int drawMode) {
        editingTool.setText(String.format(drawModeFormat, (drawMode == 1) ? "Drawing" : "Erasing"));
    }

    public void setCursorPosition(int x, int y) {
        cursor.setText(String.format(cursorPositionFormat, x, y));
    }
}