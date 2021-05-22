package com.yovelb.logic.simulator;

import com.yovelb.command.CommandExecutor;
import com.yovelb.logic.ApplicationState;
import com.yovelb.logic.ApplicationStateManager;
import com.yovelb.logic.SimulatorCommand;
import com.yovelb.model.Simulation;
import com.yovelb.model.StandardRule;
import com.yovelb.state.SimulatorState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Simulator {
    private final Timeline timeline;
    private final ApplicationStateManager applicationStateManager;
    private Simulation simulation;

    private final SimulatorState state;
    private final CommandExecutor commandExecutor;
    private boolean reset = true;

    public Simulator(ApplicationStateManager applicationStateManager, SimulatorState state, CommandExecutor commandExecutor) {
        this.applicationStateManager = applicationStateManager;
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
            applicationStateManager.getAppStateProperty().set(ApplicationState.SIMULATING);
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
        this.applicationStateManager.getAppStateProperty().set(ApplicationState.EDITING);
    }
}