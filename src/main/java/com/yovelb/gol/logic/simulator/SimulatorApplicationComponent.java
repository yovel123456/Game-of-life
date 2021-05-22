package com.yovelb.gol.logic.simulator;

import com.yovelb.gol.ApplicationComponent;
import com.yovelb.gol.ApplicationContext;
import com.yovelb.gol.model.Board;
import com.yovelb.gol.model.BoundedBoard;
import com.yovelb.gol.state.SimulatorState;

public class SimulatorApplicationComponent implements ApplicationComponent {
    @Override
    public void initComponent(ApplicationContext context) {
        SimulatorState simulatorState = context.getStateRegistry().getState(SimulatorState.class);

        Simulator simulator = new Simulator(appStateManager, simulatorState, commandExecutor);
        context.getEventBus().listenFor(SimulatorEvent.class, simulator::handle);
        editorState.getBoardProperty().listen(editorBoard -> {
            simulatorState.getBoardProperty().set(editorBoard);
            boardViewModel.getBoardProperty().set(editorBoard);
        });
        simulatorState.getBoardProperty().listen(simulationBoard -> boardViewModel.getBoardProperty().set(simulationBoard));
    }

    @Override
    public void initState(ApplicationContext context) {
        Board initialBoard = new BoundedBoard(context.getBoardWith(), context.getBoardHeight());
        SimulatorState simulatorState = new SimulatorState(initialBoard);
        context.getStateRegistry().registerState(SimulatorState.class, simulatorState);
    }
}