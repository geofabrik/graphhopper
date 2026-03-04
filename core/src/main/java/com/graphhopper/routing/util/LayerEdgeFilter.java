package com.graphhopper.routing.util;

import com.graphhopper.routing.ev.IntEncodedValue;
import com.graphhopper.util.EdgeIteratorState;

public class LayerEdgeFilter implements EdgeFilter {
    private final IntEncodedValue layerEnc;
    private final EdgeFilter filter;
    private final Integer layer;

    public LayerEdgeFilter(EdgeFilter filter, IntEncodedValue layerEnc, Integer layer) {
        this.filter = filter;
        this.layerEnc = layerEnc;
        this.layer = layer;
    }

    @Override
    public boolean accept(EdgeIteratorState edgeState) {
        return filter.accept(edgeState) && (this.layer == null || edgeState.get(layerEnc) == layer);
    }

}
