package com.yovelb.viewmodel;

import com.yovelb.model.Board;
import com.yovelb.util.Property;

public class BoardViewModel {
    private final Property<Board> boardProperty = new Property<>();

    public Property<Board> getBoardProperty() {
        return boardProperty;
    }
}