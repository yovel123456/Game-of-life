package com.yovelb;

import com.yovelb.model.CellState;
import com.yovelb.model.StandardRule;
import com.yovelb.viewmodel.ApplicationState;
import com.yovelb.viewmodel.ApplicationViewModel;
import com.yovelb.viewmodel.BoardViewModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

import static com.yovelb.model.CellState.*;

public class Toolbar extends ToolBar {
    private MainView mainView;
    private ApplicationViewModel applicationViewModel;
    private BoardViewModel boardViewModel;
    private Simulator simulator;

    public Toolbar(MainView mainView, ApplicationViewModel applicationViewModel, BoardViewModel boardViewModel) {
        this.mainView = mainView;
        this.applicationViewModel = applicationViewModel;
        this.boardViewModel = boardViewModel;
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
        this.simulator.doStep();

    }
    private void handleReset(ActionEvent event) {
        this.applicationViewModel.setCurrentState(ApplicationState.EDITING);
        simulator = null;
    }
    private void handleStart(ActionEvent event) {
        switchToSimulatingState();
        simulator.start();
    }

    private void handleStop(ActionEvent event) {
        simulator.stop();
    }

    private void switchToSimulatingState() {
        this.applicationViewModel.setCurrentState(ApplicationState.SIMULATING);
        Simulation simulation = new Simulation(boardViewModel.getBoard(), new StandardRule());
        this.simulator = new Simulator(this.boardViewModel, simulation);
    }
}