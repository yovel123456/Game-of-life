package com.yovelb.state;

import com.yovelb.model.Board;
import com.yovelb.util.Property;

public class SimulatorState {

    private final Property<Board> boardProperty = new Property<>();

    public SimulatorState(Board board) {
        this.boardProperty.set(board);
    }
    public Property<Board> getBoardProperty() {
        return boardProperty;
    }
}