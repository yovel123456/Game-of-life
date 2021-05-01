package com.yovelb.view;

import com.yovelb.model.Board;
import com.yovelb.model.CellPosition;
import com.yovelb.model.CellState;
import com.yovelb.util.Property;
import com.yovelb.util.event.EventBus;
import com.yovelb.logic.editor.BoardEvent;
import com.yovelb.viewmodel.BoardViewModel;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class SimulationCanvas extends Pane {
    private final Canvas canvas;
    private final Affine affine;
    private final BoardViewModel boardViewModel;
    private final EventBus eventBus;

    public SimulationCanvas(BoardViewModel boardViewModel, EventBus eventBus) {
        this.boardViewModel = boardViewModel;
        this.eventBus = eventBus;

        this.boardViewModel.getBoardProperty().listen(this::draw);
        this.boardViewModel.getCursorPositionProperty().listen(cellPosition -> draw(boardViewModel.getBoardProperty().get()));

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
        draw(this.boardViewModel.getBoardProperty().get());
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

    public void draw(Board newBoard) {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(affine);

        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0, 0, 400, 400);

        this.drawSimulation(newBoard);

        Property<CellPosition> cursorPositionProperty = this.boardViewModel.getCursorPositionProperty();
        if (cursorPositionProperty.isPresent()) {
            CellPosition cursor = cursorPositionProperty.get();
            g.setFill(new Color(0.3, 0.3, 0.3, 0.5));
            g.fillRect(cursor.getX(), cursor.getY(), 1, 1);
        }

        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05);
        for (int x = 0; x <= newBoard.getWidth(); x++) {
            g.strokeLine(x, 0, x, newBoard.getHeight());
        }
        for (int y = 0; y <= newBoard.getHeight(); y++) {
            g.strokeLine(0, y, newBoard.getWidth(), y);
        }
    }

    private void drawSimulation(Board simulationToDraw) {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.BLACK);
        for (int x = 0; x < simulationToDraw.getWidth(); x++) {
            for (int y = 0; y < simulationToDraw.getHeight(); y++) {
                if (simulationToDraw.getState(x, y) == CellState.ALIVE) {
                    g.fillRect(x, y, 1, 1);
                }
            }
        }
    }
}