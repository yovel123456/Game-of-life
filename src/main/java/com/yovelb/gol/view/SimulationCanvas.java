package com.yovelb.gol.view;

import com.yovelb.app.event.EventBus;
import com.yovelb.gol.components.editor.BoardEvent;
import com.yovelb.gol.model.CellPosition;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class SimulationCanvas extends Pane {
    private final Canvas canvas;
    private final Affine affine;

    private final EventBus eventBus;
    private final List<DrawLayer> drawLayers = new LinkedList<>();

    public SimulationCanvas(EventBus eventBus) {
        this.eventBus = eventBus;


        this.affine = new Affine();
        this.affine.appendScale(400 / 10f, 400 / 10f);

        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMouseClicked(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        this.canvas.setOnMouseMoved(this::handleCursorMoved);

        this.canvas.widthProperty().bind(this.widthProperty());
        this.canvas.heightProperty().bind(this.heightProperty());

        this.getChildren().add(this.canvas);
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        draw();
    }

    public void addDrawLayer(DrawLayer drawLayer) {
        drawLayers.add(drawLayer);
        drawLayers.sort(Comparator.comparingInt(DrawLayer::getLayer));
        drawLayer.addInvalidationListener(this::draw);
    }

    private void handleCursorMoved(MouseEvent event) {
        CellPosition cursorPosition = getSimulationCoordinates(event);
        this.eventBus.emit(new BoardEvent(BoardEvent.Type.CURSOR_MOVED, cursorPosition));
    }

    private void handleDraw(MouseEvent event) {
        CellPosition cursorPosition = getSimulationCoordinates(event);
        this.eventBus.emit(new BoardEvent(BoardEvent.Type.CURSOR_PRESSED, cursorPosition));
    }

    private CellPosition getSimulationCoordinates(MouseEvent event) {
        Point2D coordinates;
        try {
            coordinates = this.affine.inverseTransform(event.getX(), event.getY());
            return new CellPosition((int) coordinates.getX(), (int) coordinates.getY());
        } catch (NonInvertibleTransformException e) {
            throw new RuntimeException("Non Invertible transform");
        }
    }

    public void draw() {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        gc.setTransform(affine);

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, 400, 400);

        for (DrawLayer drawLayer : drawLayers) {
            drawLayer.draw(gc);
        }
    }
}