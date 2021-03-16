package com.yovelb.viewmodel;

import com.yovelb.Simulation;
import com.yovelb.model.StandardRule;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class SimulationViewModel {
    private final Timeline timeline;
    private final BoardViewModel boardViewModel;
    private Simulation simulation;

    public SimulationViewModel(BoardViewModel boardViewModel) {
        this.boardViewModel = boardViewModel;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(500), e -> this.doStep()));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void onAppStateChanged(ApplicationState state) {
        if (state == ApplicationState.SIMULATING) {
            this.simulation = new Simulation(boardViewModel.getBoard(), new StandardRule());
        }
    }

    public void doStep() {
        this.simulation.step();
        this.boardViewModel.setBoard(this.simulation.getBoard());
    }

    public void start() {
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }
}