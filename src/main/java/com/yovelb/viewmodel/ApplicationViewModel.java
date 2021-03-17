package com.yovelb.viewmodel;

import com.yovelb.util.Property;

public class ApplicationViewModel {
    private final Property<ApplicationState> appStateProperty = new Property<>(ApplicationState.EDITING);

    public Property<ApplicationState> getAppStateProperty() {
        return appStateProperty;
    }
}