package com.yovelb;

import com.yovelb.model.CellState;
import com.yovelb.view.SimulationCanvas;
import com.yovelb.viewmodel.ApplicationViewModel;
import com.yovelb.viewmodel.BoardViewModel;
import com.yovelb.viewmodel.EditorViewModel;
import com.yovelb.viewmodel.SimulationViewModel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MainView extends VBox {

    private final InfoBar infoBar;

    private final EditorViewModel editorViewModel;

    public MainView(ApplicationViewModel appViewModel, BoardViewModel boardViewModel,
                    EditorViewModel editorViewModel, SimulationViewModel simulationViewModel) {
        this.editorViewModel = editorViewModel;

        this.setOnKeyPressed(this::onKeyPressed);

        SimulationCanvas simulationCanvas = new SimulationCanvas(boardViewModel, editorViewModel);
        VBox.setVgrow(simulationCanvas, Priority.ALWAYS);

        Toolbar toolbar = new Toolbar(appViewModel, editorViewModel, simulationViewModel);

        this.infoBar = new InfoBar(editorViewModel);
        infoBar.setCursorPosition(0, 0);

        Pane spacer = new Pane();
        spacer.setMinSize(0, 0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(spacer, Priority.ALWAYS);

        getChildren().addAll(toolbar, simulationCanvas, spacer, infoBar);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D) {
            this.editorViewModel.setDrawMode(CellState.ALIVE);
        } else if (keyEvent.getCode() == KeyCode.E) {
            this.editorViewModel.setDrawMode(CellState.DEAD);
        }
    }
}