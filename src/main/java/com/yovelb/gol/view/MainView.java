package com.yovelb.gol.view;

import com.yovelb.gol.components.editor.DrawModeEvent;
import com.yovelb.gol.model.CellState;
import com.yovelb.app.event.EventBus;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class MainView extends BorderPane {
    private final EventBus eventBus;

    private SimulationCanvas canvas;

    public MainView(EventBus eventBus) {
        this.eventBus = eventBus;

        this.canvas = new SimulationCanvas(this.eventBus);
        Toolbar toolbar = new Toolbar(this.eventBus);

        this.setCenter(this.canvas);
        this.setTop(toolbar);

        this.setOnKeyPressed(this::onKeyPressed);
    }

    public void addDrawLayer(DrawLayer drawLayer) {
        this.canvas.addDrawLayer(drawLayer);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D) {
            this.eventBus.emit(new DrawModeEvent(CellState.ALIVE));
        } else if (keyEvent.getCode() == KeyCode.E) {
            this.eventBus.emit(new DrawModeEvent(CellState.DEAD));
        }
    }
}