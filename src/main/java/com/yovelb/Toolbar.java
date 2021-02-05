package com.yovelb;

import com.yovelb.model.CellState;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

import static com.yovelb.model.CellState.*;

public class Toolbar extends ToolBar {
    private MainView mainView;
    private Simulator simulator;

    public Toolbar(MainView mainView) {
        this.mainView = mainView;
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
        mainView.setDrawMode(ALIVE);
    }

    private void handleErase(ActionEvent event) {
        mainView.setDrawMode(DEAD);
    }

    private void handleStep(ActionEvent event) {
        switchToSimulatingState();
        mainView.getSimulation().step();
        mainView.draw();
    }
    private void handleReset(ActionEvent event) {
        mainView.setApplicationState(MainView.EDITING);
        simulator = null;
        mainView.draw();
    }
    private void handleStart(ActionEvent event) {
        switchToSimulatingState();
        simulator.start();
    }

    private void handleStop(ActionEvent event) {
        simulator.stop();
    }

    private void switchToSimulatingState() {
        if (mainView.getApplicationState() == MainView.EDITING) {
            mainView.setApplicationState(MainView.SIMULATING);
            simulator = new Simulator(mainView, mainView.getSimulation());
        }
    }

}
