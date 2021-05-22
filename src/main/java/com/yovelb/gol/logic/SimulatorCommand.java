package com.yovelb.gol.logic;

import com.yovelb.app.command.Command;
import com.yovelb.gol.state.SimulatorState;

public interface SimulatorCommand extends Command<SimulatorState> {
    @Override
    void execute(SimulatorState simulatorState);

    @Override
    default Class<SimulatorState> getStateClass() {
        return SimulatorState.class;
    }
}