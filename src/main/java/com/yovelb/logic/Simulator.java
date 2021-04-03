package com.yovelb.logic;

import com.yovelb.model.Board;
import com.yovelb.model.Simulation;
import com.yovelb.model.StandardRule;
import com.yovelb.util.Property;
import com.yovelb.viewmodel.BoardViewModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Simulator {
    private final Timeline timeline;
    private final ApplicationStateManager applicationStateManager;
    private Simulation simulation;

    private final Property<Board> initialBoard = new Property<>();
    private final Property<Board> currentBoard = new Property<>();

    public Simulator(ApplicationStateManager applicationStateManager) {
        this.applicationStateManager = applicationStateManager;

        this.timeline = new Timeline(new KeyFrame(Duration.millis(500), e -> this.doStep()));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void handle(SimulatorEvent event) {
        switch (event.getEventType()) {
            case START:
                start();
                break;
            case STOP:
                stop();
                break;
            case STEP:
                doStep();
                break;
            case RESET:
                reset();
                break;
        }
    }

    private void doStep() {
        if (applicationStateManager.getAppStateProperty().get() != ApplicationState.SIMULATING) {
            this.simulation = new Simulation(initialBoard.get(), new StandardRule());
            applicationStateManager.getAppStateProperty().set(ApplicationState.SIMULATING);
        }
        this.simulation.step();
        this.currentBoard.set(simulation.getBoard());
    }

    private void start() {
        timeline.play();
    }

    private void stop() {
        timeline.stop();
    }

    private void reset() {
        //this.simulation = new Simulation(initialBoard.get(), new StandardRule());
        this.applicationStateManager.getAppStateProperty().set(ApplicationState.EDITING);
    }

    public Property<Board> getInitialBoard() {
        return initialBoard;
    }

    public Property<Board> getCurrentBoard() {
        return currentBoard;
    }
}