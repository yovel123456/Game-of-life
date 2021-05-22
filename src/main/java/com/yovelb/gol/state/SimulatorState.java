package com.yovelb.gol.state;

import com.yovelb.gol.model.Board;
import com.yovelb.app.observable.Property;

public class SimulatorState {

    private final Property<Board> boardProperty = new Property<>();

    public SimulatorState(Board board) {
        this.boardProperty.set(board);
    }
    public Property<Board> getBoardProperty() {
        return boardProperty;
    }
}