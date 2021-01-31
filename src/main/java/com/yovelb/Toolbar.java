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

        getItems().addAll(draw, erase, step);
    }

    private void handleDraw(ActionEvent event) {
        mainView.setDrawMode(1);
    }

    private void handleErase(ActionEvent event) {
        mainView.setDrawMode(0);
    }

    private void handleStep(ActionEvent event) {
        mainView.getSimulation().step();
        mainView.draw();
    }

}
