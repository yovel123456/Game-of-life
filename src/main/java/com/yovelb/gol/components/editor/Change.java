package com.yovelb.gol.components.editor;

import com.yovelb.gol.model.CellPosition;
import com.yovelb.gol.model.CellState;

public class Change {
    private final CellPosition cellPosition;
    private final CellState nextState;
    private final CellState prevState;

    public Change(CellPosition cellPosition, CellState nextState, CellState prevState) {
        this.cellPosition = cellPosition;
        this.nextState = nextState;
        this.prevState = prevState;
    }

    public CellPosition getCellPosition() {
        return cellPosition;
    }

    public CellState getNextState() {
        return nextState;
    }

    public CellState getPrevState() {
        return prevState;
    }
}