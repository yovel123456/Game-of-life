package com.yovelb.gol.logic.board;

import com.yovelb.gol.model.Board;
import com.yovelb.gol.view.DrawLayerImplementation;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GridDrawLayer extends DrawLayerImplementation {
    private final BoardState boardState;

    public GridDrawLayer(BoardState boardState) {
        this.boardState = boardState;
        // Does not need to listen for changes since it stays the same.
        // unless it is resized which is not implemented yet.
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(0.05);

        Board board = this.boardState.getBoardProperty().get();
        for (int x = 0; x <= board.getWidth(); x++) {
            gc.strokeLine(x, 0, x, board.getHeight());
        }
        for (int y = 0; y <= board.getHeight(); y++) {
            gc.strokeLine(0, y, board.getWidth(), y);
        }
    }

    @Override
    public int getLayer() {
        return 10;
    }
}