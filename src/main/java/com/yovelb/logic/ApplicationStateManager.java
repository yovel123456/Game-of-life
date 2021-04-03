package com.yovelb.logic;

import com.yovelb.util.Property;

public class ApplicationStateManager {
    private final Property<ApplicationState> appStateProperty = new Property<>(ApplicationState.EDITING);

    public Property<ApplicationState> getAppStateProperty() {
        return appStateProperty;
    }
}