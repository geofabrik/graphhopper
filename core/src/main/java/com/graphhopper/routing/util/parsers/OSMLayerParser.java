package com.graphhopper.routing.util.parsers;

import com.graphhopper.reader.ReaderWay;
import com.graphhopper.routing.ev.EdgeIntAccess;
import com.graphhopper.routing.ev.IntEncodedValue;
import com.graphhopper.storage.IntsRef;

public class OSMLayerParser implements TagParser {
    
    private final IntEncodedValue zIndexEnc;

    public OSMLayerParser(IntEncodedValue zIndexEnc) {
        this.zIndexEnc = zIndexEnc;
    }

    @Override
    public void handleWayTags(int edgeId, EdgeIntAccess edgeIntAccess, ReaderWay way,
                    IntsRef relationFlags) {
        int layer = 0;
        String layerValue = way.getTag("layer");
        if (layerValue != null) {
            try {
                layer = Integer.parseInt(layerValue);
            } catch (NumberFormatException ex) {
            }
        }
        layer = Math.max(zIndexEnc.getMinStorableInt(), layer);
        layer = Math.min(zIndexEnc.getMaxStorableInt(), layer);
        zIndexEnc.setInt(false, edgeId, edgeIntAccess, layer);
    }

}
