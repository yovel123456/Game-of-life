package com.yovelb.view;

import com.yovelb.model.Board;
import com.yovelb.model.CellState;
import com.yovelb.viewmodel.BoardViewModel;
import com.yovelb.viewmodel.EditorViewModel;
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
    private final EditorViewModel editorViewModel;
    private final BoardViewModel boardViewModel;

    public SimulationCanvas(BoardViewModel boardViewModel, EditorViewModel editorViewModel) {
        this.boardViewModel = boardViewModel;
        this.editorViewModel = editorViewModel;

        boardViewModel.getBoardProperty().listen(this::draw);

        this.affine = new Affine();
        this.affine.appendScale(400 / 10f, 400 / 10f);

        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMouseClicked(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);

        this.canvas.widthProperty().bind(this.widthProperty());
        this.canvas.heightProperty().bind(this.heightProperty());

        this.getChildren().add(this.canvas);
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        draw(this.boardViewModel.getBoardProperty().get());
    }

    private void handleDraw(MouseEvent event) {
        Point2D coordinates = getSimulationCoordinates(event);
        this.editorViewModel.boardPress((int) coordinates.getX(), (int) coordinates.getY());
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

    public void draw(Board newBoard) {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(affine);

        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0, 0, 400, 400);

        this.drawSimulation(newBoard);

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