package com.graphhopper.routing.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.graphhopper.routing.ev.IntEncodedValue;
import com.graphhopper.routing.ev.Layer;
import com.graphhopper.storage.BaseGraph;
import com.graphhopper.util.EdgeIteratorState;

public class LayerEdgeFilterTest {
    private static LayerEdgeFilter filter(IntEncodedValue layerEnc, int layer) {
        EdgeFilter trueFilter = edgeState -> true;
        return new LayerEdgeFilter(trueFilter, layerEnc, layer);
    }

    @Test
    public void test() {
        EncodingManager em = new EncodingManager.Builder().add(Layer.create()).build();
        IntEncodedValue layerEnc = em.getIntEncodedValue(Layer.KEY);
        BaseGraph graph = new BaseGraph.Builder(em).create();
        EdgeIteratorState edge = graph.edge(0, 1).setDistance(1);
        edge.set(layerEnc, 0);
        assertTrue(filter(layerEnc, 0).accept(edge));
        edge.set(layerEnc, 1);
        assertFalse(filter(layerEnc, 0).accept(edge));
        assertTrue(filter(layerEnc, 1).accept(edge));
        assertFalse(filter(layerEnc, -5).accept(edge));
        edge.set(layerEnc, -6);
        assertFalse(filter(layerEnc, 0).accept(edge));
        assertFalse(filter(layerEnc, 1).accept(edge));
        assertTrue(filter(layerEnc, -6).accept(edge));
    }
}
