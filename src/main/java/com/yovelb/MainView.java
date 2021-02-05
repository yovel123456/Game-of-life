package com.yovelb;

import com.yovelb.model.Board;
import com.yovelb.model.BoundedBoard;
import com.yovelb.model.CellState;
import com.yovelb.model.StandardRule;
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

import static com.yovelb.model.CellState.*;

public class MainView extends VBox {
    public static final int EDITING = 0;
    public static final int SIMULATING = 1;

    private final Canvas canvas;
    private final InfoBar infoBar;

    private final Affine affine;

    private Simulation simulation;
    private Board initialBoard;

    private CellState drawMode = ALIVE;
    private int applicationState = EDITING;

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

        this.initialBoard = new BoundedBoard(10, 10);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D) {
            setDrawMode(ALIVE);
        } else if (keyEvent.getCode() == KeyCode.E) {
            setDrawMode(DEAD);
        }
    }

    private void handleMoved(MouseEvent event) {
        Point2D coordinates = getSimulationCoordinates(event);
        infoBar.setCursorPosition((int) coordinates.getX(), (int) coordinates.getY());
    }

    private void handleDraw(MouseEvent event) {
        if (applicationState == SIMULATING) {
            return;
        }
        Point2D coordinates = getSimulationCoordinates(event);
        initialBoard.setState((int) coordinates.getX(), (int) coordinates.getY(), drawMode);
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

        if (applicationState == EDITING) {
            drawSimulation(initialBoard);
        } else {
            drawSimulation(simulation.getBoard());
        }

        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05);
        for (int x = 0; x <= initialBoard.getWidth(); x++) {
            g.strokeLine(x, 0, x,10);
        }
        for (int y = 0; y <= initialBoard.getHeight(); y++) {
            g.strokeLine(0, y, 10, y);
        }
    }

    private void drawSimulation(Board simulationToDraw) {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.BLACK);
        for (int x = 0; x < simulationToDraw.getWidth(); x++) {
            for (int y = 0; y < simulationToDraw.getHeight(); y++) {
                if (simulationToDraw.getState(x, y) == ALIVE) {
                    g.fillRect(x, y, 1, 1);
                }
            }
        }
    }

    public Simulation getSimulation() {
        return simulation;
    }


    public void setDrawMode(CellState mode) {
        this.drawMode = mode;
        this.infoBar.setDrawMode(drawMode);
    }

    public void setApplicationState(int applicationState) {
        if (this.applicationState == applicationState) {
            return;
        }
        if (applicationState == SIMULATING) {
            simulation = new Simulation(initialBoard, new StandardRule());
        }
        this.applicationState = applicationState;
    }
    public int getApplicationState() {
        return applicationState;
    }
}