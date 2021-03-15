package com.yovelb;

import com.yovelb.model.Board;
import com.yovelb.model.CellState;
import com.yovelb.model.StandardRule;
import com.yovelb.viewmodel.ApplicationState;
import com.yovelb.viewmodel.ApplicationViewModel;
import com.yovelb.viewmodel.BoardViewModel;
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

    private final Canvas canvas;
    private final InfoBar infoBar;

    private final Affine affine;

    private Board board;

    private CellState drawMode = ALIVE;

    private ApplicationViewModel appViewModel;
    private BoardViewModel boardViewModel;

    private boolean isDrawingEnabled = true;

    public MainView(ApplicationViewModel appViewModel, BoardViewModel boardViewModel, Board initialBoard) {
        this.appViewModel = appViewModel;
        this.boardViewModel = boardViewModel;
        this.board = initialBoard;

        this.appViewModel.listenToAppState(this::onApplicationStateChanged);
        this.boardViewModel.listenToBoard(this::onBoardChanged);

        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMouseClicked(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        this.canvas.setOnMouseMoved(this::handleMoved);

        this.setOnKeyPressed(this::onKeyPressed);

        Toolbar toolbar = new Toolbar(this, appViewModel, boardViewModel);

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
    }

    private void onBoardChanged(Board board) {
        draw(board);
    }

    private void onApplicationStateChanged(ApplicationState state) {
        if (state == ApplicationState.EDITING) {
            this.isDrawingEnabled = true;
            this.boardViewModel.setBoard(this.board);
        } else if (state == ApplicationState.SIMULATING) {
            this.isDrawingEnabled = false;
        } else {
            throw new IllegalArgumentException("Unsupported ApplicationState: " + state.name());
        }
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
        if (!isDrawingEnabled) {
            return;
        }
        Point2D coordinates = getSimulationCoordinates(event);
        board.setState((int) coordinates.getX(), (int) coordinates.getY(), drawMode);
        this.boardViewModel.setBoard(this.board);
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
            g.strokeLine(x, 0, x,10);
        }
        for (int y = 0; y <= newBoard.getHeight(); y++) {
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

    public void setDrawMode(CellState mode) {
        this.drawMode = mode;
        this.infoBar.setDrawMode(drawMode);
    }
}