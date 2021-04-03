package com.yovelb.logic;

import com.yovelb.command.Command;
import com.yovelb.state.EditorState;

public interface EditorCommand extends Command<EditorState> {
    @Override
    void execute(EditorState editorState);
}
