package com.yovelb;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox {
    private final Canvas canvas;
    private final InfoBar infoBar;

    private final Affine affine;

    private final Simulation simulation;

    private int drawMode = 1;

    public MainView() {
        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMouseClicked(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        this.canvas.setOnMouseMoved(this::handleMoved);

        this.setOnKeyPressed(this::onKeyPressed);

        Toolbar toolbar = new Toolbar(this);

        this.infoBar = new InfoBar();
        infoBar.setDrawMode(drawMode);
        infoBar.setCursorPosition(0, 0);

        Pane spacer = new Pane();
        spacer.setMinSize(0, 0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(spacer, Priority.ALWAYS);

        getChildren().addAll(toolbar,canvas, spacer, infoBar);

        this.affine = new Affine();
        this.affine.appendScale(400 / 10f, 400 / 10f);

        this.simulation = new Simulation(10, 10);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D) {
            setDrawMode(1);
        } else if (keyEvent.getCode() == KeyCode.E) {
            setDrawMode(0);
        }
    }

    private void handleMoved(MouseEvent event) {
        Point2D coordinates = getSimulationCoordinates(event);
        infoBar.setCursorPosition((int) coordinates.getX(), (int) coordinates.getY());
    }

    private void handleDraw(MouseEvent event) {
        Point2D coordinates = getSimulationCoordinates(event);
        simulation.setState((int) coordinates.getX(), (int) coordinates.getY(), drawMode);
        draw();
    }

    private Point2D getSimulationCoordinates(MouseEvent event) {
        Point2D coordinates;
        try {
            coordinates = this.affine.inverseTransform(event.getX(), event.getY());
        } catch (NonInvertibleTransformException e) {
            throw new RuntimeException("Non Invertible transform");
        }
        return coordinates;
    }

    public void draw() {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(affine);

        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0, 0, 400, 400);

        g.setFill(Color.BLACK);
        for (int x = 0; x < this.simulation.width; x++) {
            for (int y = 0; y < this.simulation.height; y++) {
                if (this.simulation.getState(x, y) == Simulation.ALIVE) {
                    g.fillRect(x, y, 1, 1);
                }
            }
        }
        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05);
        for (int x = 0; x <= this.simulation.width; x++) {
            g.strokeLine(x, 0, x,10);
        }
        for (int y = 0; y <= this.simulation.height; y++) {
            g.strokeLine(0, y, 10, y);
        }
    }

    public void setDrawMode(int mode) {
        this.drawMode = mode;
        this.infoBar.setDrawMode(drawMode);
    }

    public Simulation getSimulation() {
        return simulation;
    }
}
