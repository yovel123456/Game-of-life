package com.yovelb.gol.components.editor;

import com.yovelb.gol.ApplicationComponent;
import com.yovelb.gol.ApplicationContext;
import com.yovelb.gol.components.board.BoardState;
import com.yovelb.gol.components.simulator.SimulatorEvent;
import com.yovelb.gol.model.Board;
import com.yovelb.gol.model.BoundedBoard;

public class EditorApplicationComponent implements ApplicationComponent {
    @Override
    public void initComponent(ApplicationContext context) {
        EditorState editorState = context.getStateRegistry().getState(EditorState.class);
        BoardState boardState = context.getStateRegistry().getState(BoardState.class);

        Editor editor = new Editor(editorState, context.getCommandExecutor());
        context.getEventBus().listenFor(DrawModeEvent.class, editor::handle);
        context.getEventBus().listenFor(BoardEvent.class, editor::handle);
        context.getEventBus().listenFor(SimulatorEvent.class, editor::handleSimulatorEvent);
        context.getEventBus().listenFor(SimulatorEvent.class, event ->  {
            if (event.getEventType() == SimulatorEvent.Type.RESET) {
                boardState.getBoardProperty().set(editorState.getBoardProperty().get());
            }
        });

        editorState.getBoardProperty().listen(boardState.getBoardProperty()::set);

        ToolDrawLayer toolDrawLayer = new ToolDrawLayer(editorState);
        context.getMainView().addDrawLayer(toolDrawLayer);

        CurrentEditDrawLayer editDrawLayer = new CurrentEditDrawLayer(editorState);
        context.getMainView().addDrawLayer(editDrawLayer);
    }

    @Override
    public void initState(ApplicationContext context) {
        Board initialBoard = new BoundedBoard(context.getBoardWith(), context.getBoardHeight());
        EditorState editorState = new EditorState(initialBoard);
        context.getStateRegistry().registerState(EditorState.class, editorState);
    }
}