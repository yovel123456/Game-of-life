package com.yovelb;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox {
    private Canvas canvas;

    private Affine affine;

    private Simulation simulation;

    private int drawMode = 1;

    public MainView() {

        Toolbar toolbar = new Toolbar(this);

        canvas = new Canvas(400, 400);
        canvas.setOnMouseClicked(this::handleDraw);
        canvas.setOnMouseDragged(this::handleDraw);

        this.setOnKeyPressed(this::onKeyPressed);

        getChildren().addAll(toolbar ,canvas);

        affine = new Affine();
        affine.appendScale(400 / 10f, 400 / 10f);

        simulation = new Simulation(10, 10);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D) {
            drawMode = 1;
        } else if (keyEvent.getCode() == KeyCode.E) {
            drawMode = 0;
        }
    }

    private void handleDraw(MouseEvent event) {
        Point2D coordinates = null;
        try {
            coordinates = this.affine.inverseTransform(event.getX(), event.getY());
            simulation.setState((int) coordinates.getX(), (int) coordinates.getY(), drawMode);
            draw();
        } catch (NonInvertibleTransformException e) {
            System.out.println("Could not invert transform");
        }
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
    }

    public Simulation getSimulation() {
        return simulation;
    }
}
