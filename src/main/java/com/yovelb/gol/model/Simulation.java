package com.yovelb.gol.model;

public class Simulation {
    private Board board;
    private final SimulationRule simulationRule;

    public Simulation(Board board, SimulationRule simulationRule) {
        this.board = board;
        this.simulationRule = simulationRule;
    }
    public void step() {
        Board newBoard = board.copy();
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                newBoard.setState(x, y, simulationRule.getNextState(x, y, board));
            }
        }
        this.board = newBoard;
    }
    public Board getBoard() {
        return board;
    }
}