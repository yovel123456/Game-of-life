package com.yovelb.gol.model;

public interface Board {

    Board copy();

    CellState getState(int x, int y);

    void setState(int x, int y, CellState state);

    int getWidth();
    int getHeight();
}
