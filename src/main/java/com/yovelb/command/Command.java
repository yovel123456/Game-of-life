package com.yovelb.command;

public interface Command<T> {
    void execute(T t);
}
