package com.yovelb;

import com.yovelb.model.Board;
import com.yovelb.model.BoundedBoard;
import com.yovelb.model.CellState;
import com.yovelb.model.StandardRule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    @Test
    void simulateEntireBounds() {
        Board board = new BoundedBoard(5, 3);
        board.setState(0, 0, CellState.ALIVE);
        board.setState(4, 0, CellState.ALIVE);
        board.setState(4, 2, CellState.ALIVE);
        board.setState(0, 2, CellState.ALIVE);

        Simulation simulation = new Simulation(board, new StandardRule());

        simulation.step();


        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                assertEquals(CellState.DEAD, simulation.getBoard().getState(x, y));
            }
        }
    }
}