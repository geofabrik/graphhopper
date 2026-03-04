package com.graphhopper.routing.ev;

public class Layer {
    public static final String KEY = "layer";

    public static IntEncodedValue create() {
        return new IntEncodedValueImpl(KEY, 4, -7, false, false);
    }
}
