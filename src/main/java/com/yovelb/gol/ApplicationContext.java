package com.yovelb.gol;

import com.yovelb.app.command.CommandExecutor;
import com.yovelb.app.event.EventBus;
import com.yovelb.app.state.StateRegistry;
import com.yovelb.gol.view.MainView;

public class ApplicationContext {

    private final EventBus eventBus;
    private final StateRegistry stateRegistry;
    private final CommandExecutor commandExecutor;

    private final MainView mainView;

    private int boardWith;
    private int boardHeight;

    public ApplicationContext(EventBus eventBus, CommandExecutor commandExecutor, StateRegistry stateRegistry, MainView mainView, int boardWith, int boardHeight) {
        this.eventBus = eventBus;
        this.stateRegistry = stateRegistry;
        this.commandExecutor = commandExecutor;
        this.mainView = mainView;
        this.boardWith = boardWith;
        this.boardHeight = boardHeight;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public StateRegistry getStateRegistry() {
        return stateRegistry;
    }

    public CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }

    public MainView getMainView() {
        return mainView;
    }

    public int getBoardWith() {
        return boardWith;
    }

    public int getBoardHeight() {
        return boardHeight;
    }
}