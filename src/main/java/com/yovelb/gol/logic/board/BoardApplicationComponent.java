package com.yovelb.gol.logic.board;

import com.yovelb.gol.ApplicationComponent;
import com.yovelb.gol.ApplicationContext;
import com.yovelb.gol.model.Board;
import com.yovelb.gol.model.BoundedBoard;

public class BoardApplicationComponent implements ApplicationComponent {
    @Override
    public void initComponent(ApplicationContext context) {

    }

    @Override
    public void initState(ApplicationContext context) {
        Board initialBoard = new BoundedBoard(context.getBoardWith(), context.getBoardHeight());
        BoardState boardState = new BoardState(initialBoard);
        context.getStateRegistry().registerState(BoardState.class, boardState);
    }
}