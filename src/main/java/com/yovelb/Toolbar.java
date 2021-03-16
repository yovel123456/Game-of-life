package com.yovelb;

import com.yovelb.model.CellState;
import com.yovelb.viewmodel.ApplicationState;
import com.yovelb.viewmodel.ApplicationViewModel;
import com.yovelb.viewmodel.EditorViewModel;
import com.yovelb.viewmodel.SimulationViewModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {
    private final ApplicationViewModel applicationViewModel;
    private final SimulationViewModel simulationViewModel;
    private final EditorViewModel editorViewModel;

    public Toolbar(ApplicationViewModel applicationViewModel, EditorViewModel editorViewModel, SimulationViewModel simulationViewModel) {
        this.applicationViewModel = applicationViewModel;
        this.simulationViewModel = simulationViewModel;
        this.editorViewModel = editorViewModel;
        Button draw = new Button("Draw");
        draw.setOnAction(this::handleDraw);
        Button erase = new Button("Erase");
        erase.setOnAction(this::handleErase);
        Button step = new Button("Step");
        step.setOnAction(this::handleStep);
        Button reset = new Button("Reset");
        reset.setOnAction(this::handleReset);
        Button start = new Button("Start");
        start.setOnAction(this::handleStart);
        Button stop = new Button("Stop");
        stop.setOnAction(this::handleStop);

        getItems().addAll(draw, erase, reset, step, start, stop);
    }

    private void handleDraw(ActionEvent event) {
        this.editorViewModel.setDrawMode(CellState.ALIVE);
    }

    private void handleErase(ActionEvent event) {
        this.editorViewModel.setDrawMode(CellState.DEAD);
    }

    private void handleStep(ActionEvent event) {
        switchToSimulatingState();
        this.simulationViewModel.doStep();

    }
    private void handleReset(ActionEvent event) {
        this.applicationViewModel.setCurrentState(ApplicationState.EDITING);
    }
    private void handleStart(ActionEvent event) {
        switchToSimulatingState();
        simulationViewModel.start();
    }

    private void handleStop(ActionEvent event) {
        simulationViewModel.stop();
    }

    private void switchToSimulatingState() {
        this.applicationViewModel.setCurrentState(ApplicationState.SIMULATING);
    }
}