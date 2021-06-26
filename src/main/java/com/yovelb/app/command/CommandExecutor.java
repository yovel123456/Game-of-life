package com.yovelb.app.command;

import com.yovelb.app.state.StateRegistry;

import java.util.Stack;

public class CommandExecutor {
    private final StateRegistry stateRegistry;

    private Stack<UndoableCommand> commandStack = new Stack<>();

    public CommandExecutor(StateRegistry stateRegistry) {
        this.stateRegistry = stateRegistry;
    }

    public <T> void execute(Command<T> command) {
        final T state = stateRegistry.getState(command.getStateClass());
        command.execute(state);
    }

    public <T> void execute(UndoableCommand<T> command) {
        final T state = stateRegistry.getState(command.getStateClass());
        command.execute(state);
        commandStack.push(command);
    }

    public void undo() {
        if (!commandStack.isEmpty()) {
            UndoableCommand command = commandStack.pop();

            Object state = stateRegistry.getState(command.getStateClass());
            command.undo(state);
        }
    }
}