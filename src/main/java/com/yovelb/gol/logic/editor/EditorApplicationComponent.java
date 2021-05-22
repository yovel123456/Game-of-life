package com.yovelb.gol.logic.editor;

import com.yovelb.gol.ApplicationComponent;
import com.yovelb.gol.ApplicationContext;
import com.yovelb.gol.logic.ApplicationState;
import com.yovelb.gol.model.Board;
import com.yovelb.gol.model.BoundedBoard;
import com.yovelb.gol.state.EditorState;

public class EditorApplicationComponent implements ApplicationComponent {
    @Override
    public void initComponent(ApplicationContext context) {
        EditorState editorState = context.getStateRegistry().getState(EditorState.class);

        Editor editor = new Editor(editorState, context.getCommandExecutor());
        context.getEventBus().listenFor(DrawModeEvent.class, editor::handle);
        context.getEventBus().listenFor(BoardEvent.class, editor::handle);

        editorState.getCursorPositionProperty().listen(cursorPosition -> boardViewModel.getCursorPositionProperty().set(cursorPosition));

        appStateManager.getAppStateProperty().listen(editor::onAppStateChanged);
        appStateManager.getAppStateProperty().listen(newState -> {
            if (newState == ApplicationState.EDITING) {
                Board currentBoard = editorState.getBoardProperty().get();
                boardViewModel.getBoardProperty().set(currentBoard);
                simulatorState.getBoardProperty().set(currentBoard);
            }
        });
    }

    @Override
    public void initState(ApplicationContext context) {
        Board initialBoard = new BoundedBoard(context.getBoardWith(), context.getBoardHeight());
        EditorState editorState = new EditorState(initialBoard);
        context.getStateRegistry().registerState(EditorState.class, editorState);
    }
}