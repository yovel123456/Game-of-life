package com.yovelb.gol.logic.simulator;

import com.yovelb.gol.ApplicationComponent;
import com.yovelb.gol.ApplicationContext;
import com.yovelb.gol.logic.board.BoardState;
import com.yovelb.gol.model.Board;
import com.yovelb.gol.model.BoundedBoard;
import com.yovelb.gol.state.EditorState;
import com.yovelb.gol.state.SimulatorState;

public class SimulatorApplicationComponent implements ApplicationComponent {
    @Override
    public void initComponent(ApplicationContext context) {
        SimulatorState simulatorState = context.getStateRegistry().getState(SimulatorState.class);
        EditorState editorState = context.getStateRegistry().getState(EditorState.class);
        BoardState boardState = context.getStateRegistry().getState(BoardState.class);

        Simulator simulator = new Simulator(simulatorState, context.getCommandExecutor());
        context.getEventBus().listenFor(SimulatorEvent.class, simulator::handle);

        editorState.getBoardProperty().listen(simulatorState.getBoardProperty()::set);

        simulatorState.getBoardProperty().listen(simulationBoard -> {
            if (simulatorState.getSimulating().get()) {
                boardState.getBoardProperty().set(simulationBoard);
            }
        });
    }

    @Override
    public void initState(ApplicationContext context) {
        Board initialBoard = new BoundedBoard(context.getBoardWith(), context.getBoardHeight());
        SimulatorState simulatorState = new SimulatorState(initialBoard);
        context.getStateRegistry().registerState(SimulatorState.class, simulatorState);
    }
}