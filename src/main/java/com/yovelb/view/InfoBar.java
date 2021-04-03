package com.yovelb.view;

import com.yovelb.model.CellPosition;
import com.yovelb.model.CellState;
import com.yovelb.viewmodel.InfoBarViewModel;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class InfoBar extends HBox {
    private final Label cursor;
    private final Label editingTool;

    public InfoBar(InfoBarViewModel infoBarViewModel) {
        infoBarViewModel.getCurrentDrawModeProperty().listen(this::setDrawMode);
        infoBarViewModel.getCursorPositionProperty().listen(this::setCursorPosition);

        this.cursor = new Label("Cursor: (0, 0)");
        this.editingTool = new Label("Draw Mode: Drawing");

        Pane spacer = new Pane();
        spacer.setMinSize(0, 0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(cursor, editingTool);
    }
    private void setDrawMode(CellState drawMode) {
        String drawModeFormat = "Draw Mode: %s";
        this.editingTool.setText(String.format(drawModeFormat, (drawMode == CellState.ALIVE) ? "Drawing" : "Erasing"));
    }

    private void setCursorPosition(CellPosition cellPosition) {
        String cursorPositionFormat = "Cursor: (%d, %d)";
        this.cursor.setText(String.format(cursorPositionFormat, cellPosition.getX(), cellPosition.getY()));
    }
}