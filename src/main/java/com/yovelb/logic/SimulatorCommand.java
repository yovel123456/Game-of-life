package com.yovelb.logic;

import com.yovelb.command.Command;
import com.yovelb.state.SimulatorState;

public interface SimulatorCommand extends Command<SimulatorState> {
    @Override
    void execute(SimulatorState simulatorState);

    @Override
    default Class<SimulatorState> getStateClass() {
        return SimulatorState.class;
    }
}