package com.yovelb.gol.logic.simulator;

import com.yovelb.app.command.CommandExecutor;
import com.yovelb.gol.logic.SimulatorCommand;
import com.yovelb.gol.model.Simulation;
import com.yovelb.gol.model.StandardRule;
import com.yovelb.gol.state.SimulatorState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Simulator {
    private final Timeline timeline;
    private Simulation simulation;

    private final SimulatorState state;
    private final CommandExecutor commandExecutor;
    private boolean reset = true;

    public Simulator(SimulatorState state, CommandExecutor commandExecutor) {
        this.state = state;
        this.commandExecutor = commandExecutor;

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
        if (reset) {
            reset = false;
            this.simulation = new Simulation(state.getBoardProperty().get(), new StandardRule());
            this.state.getSimulating().set(true);
        }
        this.simulation.step();
        SimulatorCommand command = (state) -> state.getBoardProperty().set(simulation.getBoard());
        commandExecutor.execute(command);
    }

    private void start() {
        timeline.play();
    }

    private void stop() {
        timeline.stop();
    }

    private void reset() {
        reset = true;
        this.state.getSimulating().set(true);
    }
}