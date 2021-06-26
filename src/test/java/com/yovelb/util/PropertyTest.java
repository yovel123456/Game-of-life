package com.yovelb.util;

import com.yovelb.app.observable.Property;
import com.yovelb.app.observable.ChangeListener;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTest {

    @Test
    void constructWithInitialValue() {
        String expected = "Hello World";
        Property<String> stringProperty = new Property<>(expected);

        assertEquals(expected, stringProperty.get());
    }

    @Test
    void constructWithNoValue() {
        Property<String> stringProperty = new Property<>();

        assertNull(stringProperty.get());
    }

    @Test
    void notifiesListener() {
        double expected = 2.0;
        DoubleListener listener = new DoubleListener();
        Property<Double> doubleProperty = new Property<>(1.0);
        doubleProperty.listen(listener);

        doubleProperty.set(expected);

        assertTrue(listener.notified);
        assertEquals(expected, listener.value);
    }

    private static class DoubleListener implements ChangeListener<Double> {
        private boolean notified = false;
        private double value;

        @Override
        public void onChanged(Double value) {
            notified = true;
            this.value = value;
        }
    }
}