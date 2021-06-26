package com.yovelb.gol.components.board;

import com.yovelb.gol.ApplicationComponent;
import com.yovelb.gol.ApplicationContext;
import com.yovelb.gol.model.Board;
import com.yovelb.gol.model.BoundedBoard;

public class BoardApplicationComponent implements ApplicationComponent {
    @Override
    public void initComponent(ApplicationContext context) {
        BoardState state = context.getStateRegistry().getState(BoardState.class);

        BoardDrawLayer boardDrawLayer = new BoardDrawLayer(state);
        GridDrawLayer gridDrawLayer = new GridDrawLayer(state);

        context.getMainView().addDrawLayer(boardDrawLayer);
        context.getMainView().addDrawLayer(gridDrawLayer);
    }

    @Override
    public void initState(ApplicationContext context) {
        Board initialBoard = new BoundedBoard(context.getBoardWith(), context.getBoardHeight());
        BoardState boardState = new BoardState(initialBoard);
        context.getStateRegistry().registerState(BoardState.class, boardState);
    }
}