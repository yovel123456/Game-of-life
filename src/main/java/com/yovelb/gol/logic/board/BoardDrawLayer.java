package com.yovelb.gol.logic.board;

import com.yovelb.gol.model.Board;
import com.yovelb.gol.model.CellState;
import com.yovelb.gol.view.DrawLayerImplementation;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BoardDrawLayer extends DrawLayerImplementation {
    private final BoardState boardState;

    public BoardDrawLayer(BoardState boardState) {
        this.boardState = boardState;
        this.boardState.getBoardProperty().listen(b -> this.invalidate());
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);

        Board board = boardState.getBoardProperty().get();
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                if (board.getState(x, y) == CellState.ALIVE) {
                    gc.fillRect(x, y, 1, 1);
                }
            }
        }
    }

    @Override
    public int getLayer() {
        return 0;
    }
}