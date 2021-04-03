package com.yovelb.view;

import com.yovelb.logic.DrawModeEvent;
import com.yovelb.logic.SimulatorEvent;
import com.yovelb.model.CellState;
import com.yovelb.util.event.EventBus;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {
    private final EventBus eventBus;

    public Toolbar(EventBus eventBus) {
        this.eventBus = eventBus;
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
        this.eventBus.emit(new DrawModeEvent(CellState.ALIVE));
    }

    private void handleErase(ActionEvent event) {
        this.eventBus.emit(new DrawModeEvent(CellState.DEAD));
    }

    private void handleStep(ActionEvent event) {
        this.eventBus.emit(new SimulatorEvent(SimulatorEvent.Type.STEP));

    }
    private void handleReset(ActionEvent event) {
        this.eventBus.emit(new SimulatorEvent(SimulatorEvent.Type.RESET));
    }

    private void handleStart(ActionEvent event) {
        this.eventBus.emit(new SimulatorEvent(SimulatorEvent.Type.START));
    }

    private void handleStop(ActionEvent event) {
        this.eventBus.emit(new SimulatorEvent(SimulatorEvent.Type.STOP));
    }
}