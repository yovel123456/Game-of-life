package com.yovelb;

import com.yovelb.model.Board;
import com.yovelb.model.SimulationRule;

public class Simulation {
    private Board board;
    private final SimulationRule simulationRule;

    public Simulation(Board board, SimulationRule simulationRule) {
        this.board = board;
        this.simulationRule = simulationRule;
    }
    public void step() {
        Board newBoard = board.copy();
        for (int y = 0; y < board.getWidth(); y++) {
            for (int x = 0; x < board.getHeight(); x++) {
                newBoard.setState(x, y, simulationRule.getNextState(x, y, board));
            }
        }
        this.board = newBoard;
    }
    public Board getBoard() {
        return board;
    }
}