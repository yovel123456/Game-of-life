package com.yovelb.gol.logic;

import com.yovelb.app.observable.Property;

public class ApplicationStateManager {
    private final Property<ApplicationState> appStateProperty = new Property<>(ApplicationState.EDITING);

    public Property<ApplicationState> getAppStateProperty() {
        return appStateProperty;
    }
}