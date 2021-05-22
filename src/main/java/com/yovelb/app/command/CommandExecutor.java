package com.yovelb.app.command;

import com.yovelb.app.state.StateRegistry;

public class CommandExecutor {
    private StateRegistry stateRegistry;

    public CommandExecutor(StateRegistry stateRegistry) {
        this.stateRegistry = stateRegistry;
    }

    public <T> void execute(Command<T> command) {
        final T state = stateRegistry.getState(command.getStateClass());
        command.execute(state);
    }
}