package com.yovelb.gol.model;

import static com.yovelb.gol.model.CellState.*;

public class StandardRule implements SimulationRule{
    @Override
    public CellState getNextState(int x, int y, Board board) {
        int aliveNeighbours = countAliveNeighbours(x, y, board);
        if (board.getState(x, y) == ALIVE) {
            if (aliveNeighbours < 2) {
                return DEAD;
            } else if (aliveNeighbours == 2 || aliveNeighbours == 3) {
                return ALIVE;
            } else {
                return DEAD;
            }
        } else {
            if (aliveNeighbours == 3) {
                return ALIVE;
            }
        }
        return DEAD;
    }
    public int countAliveNeighbours(int x, int y, Board board) {
        int count = 0;
        count += countCell(x - 1, y - 1, board);
        count += countCell(x, y - 1, board);
        count += countCell(x + 1, y - 1, board);

        count += countCell(x - 1, y, board);
        count += countCell(x + 1, y, board);

        count += countCell(x - 1, y + 1, board);
        count += countCell(x, y + 1, board);
        count += countCell(x + 1, y + 1, board);

        return count;
    }
    private int countCell(int x, int y, Board board) {
        return (board.getState(x, y) == ALIVE) ? 1 : 0;
    }
}