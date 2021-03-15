package com.yovelb;

import com.yovelb.viewmodel.BoardViewModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class Simulator {
    private Timeline timeline;
    private BoardViewModel boardViewModel;
    private Simulation simulation;

    public Simulator(BoardViewModel boardViewModel, Simulation simulation) {
        this.boardViewModel = boardViewModel;
        this.simulation = simulation;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(500), e -> this.doStep()));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
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
