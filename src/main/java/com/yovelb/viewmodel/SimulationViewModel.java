package com.yovelb.viewmodel;

import com.yovelb.Simulation;
import com.yovelb.model.StandardRule;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class SimulationViewModel {
    private final Timeline timeline;
    private final BoardViewModel boardViewModel;
    private final ApplicationViewModel applicationViewModel;
    private final EditorViewModel editorViewModel;
    private Simulation simulation;

    public SimulationViewModel(BoardViewModel boardViewModel, ApplicationViewModel applicationViewModel, EditorViewModel editorViewModel) {
        this.boardViewModel = boardViewModel;
        this.applicationViewModel = applicationViewModel;
        this.editorViewModel = editorViewModel;

        this.timeline = new Timeline(new KeyFrame(Duration.millis(500), e -> this.doStep()));
        this.timeline.setCycleCount(Timeline.INDEFINITE);

        this.simulation = new Simulation(editorViewModel.getBoard(), new StandardRule());
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
        if (applicationViewModel.getAppStateProperty().get() != ApplicationState.SIMULATING) {
            applicationViewModel.getAppStateProperty().set(ApplicationState.SIMULATING);
        }
        this.simulation.step();
        this.boardViewModel.getBoardProperty().set(this.simulation.getBoard());
    }

    private void start() {
        timeline.play();
    }

    private void stop() {
        timeline.stop();
    }

    private void reset() {
        this.simulation = new Simulation(editorViewModel.getBoard(), new StandardRule());
        this.applicationViewModel.getAppStateProperty().set(ApplicationState.EDITING);
    }
}