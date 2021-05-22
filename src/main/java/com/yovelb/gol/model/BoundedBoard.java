package com.yovelb.gol.model;

import static com.yovelb.gol.model.CellState.*;

public class BoundedBoard implements Board{
    private final int width;
    private final int height;
    private final CellState[][] board;

    public BoundedBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new CellState[width][height];

        initializeBoard(width, height);
    }

    private void initializeBoard(int width, int height) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                setState(x, y, DEAD);
            }
        }
    }
    @Override
    public BoundedBoard copy() {
        BoundedBoard newBoard = new BoundedBoard(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                newBoard.setState(x, y, this.getState(x, y));
            }
        }
        return newBoard;
    }
    @Override
    public CellState getState(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return DEAD;
        }
        return board[x][y];
    }

    @Override
    public void setState(int x, int y, CellState state) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return;
        }
        board[x][y] = state;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}