package com.yovelb.app.observable;

public interface ChangeListener<V> {
    void onChanged(V value);
}