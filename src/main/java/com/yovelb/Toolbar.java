package com.yovelb;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {
    private MainView mainView;

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
        mainView.setDrawMode(1);
    }

    private void handleErase(ActionEvent event) {
        mainView.setDrawMode(0);
    }

    private void handleStep(ActionEvent event) {
        mainView.setApplicationState(MainView.SIMULATING);
        mainView.getSimulation().step();
        mainView.draw();
    }
    private void handleReset(ActionEvent event) {
        mainView.setApplicationState(MainView.EDITING);
        mainView.draw();
    }
    private void handleStart(ActionEvent event) {
        mainView.setApplicationState(MainView.SIMULATING);
        mainView.getSimulator().start();
    }

    private void handleStop(ActionEvent event) {
        mainView.getSimulator().stop();
    }

}
