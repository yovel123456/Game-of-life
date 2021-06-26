package com.yovelb.app.command;

public interface UndoableCommand<T> extends Command<T> {

    void undo(T state);
}
