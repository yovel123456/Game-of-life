package com.yovelb.gol.components.board;

import com.yovelb.app.observable.Property;
import com.yovelb.gol.model.Board;

public class BoardState {
    private final Property<Board> boardProperty = new Property<>();

    public BoardState(Board board) {
        this.boardProperty.set(board);
    }

    public Property<Board> getBoardProperty() {
        return boardProperty;
    }
}