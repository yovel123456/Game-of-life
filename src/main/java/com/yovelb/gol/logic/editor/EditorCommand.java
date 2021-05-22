package com.yovelb.gol.logic.editor;

import com.yovelb.app.command.Command;
import com.yovelb.gol.state.EditorState;

public interface EditorCommand extends Command<EditorState> {
    @Override
    void execute(EditorState editorState);

    @Override
    default Class<EditorState> getStateClass() {
        return EditorState.class;
    }
}
