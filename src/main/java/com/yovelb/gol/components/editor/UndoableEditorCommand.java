package com.yovelb.gol.components.editor;

import com.yovelb.app.command.UndoableCommand;

public interface UndoableEditorCommand extends UndoableCommand<EditorState> {
    @Override
    void undo(EditorState state);

    @Override
    void execute(EditorState editorState);

    @Override
    default Class<EditorState> getStateClass() {
        return EditorState.class;
    }
}