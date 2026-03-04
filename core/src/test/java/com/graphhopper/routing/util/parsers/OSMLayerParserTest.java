package com.graphhopper.routing.util.parsers;

import com.graphhopper.reader.ReaderWay;
import com.graphhopper.routing.ev.ArrayEdgeIntAccess;
import com.graphhopper.routing.ev.EdgeIntAccess;
import com.graphhopper.routing.ev.EncodedValue;
import com.graphhopper.routing.ev.IntEncodedValue;
import com.graphhopper.routing.ev.Layer;
import com.graphhopper.storage.IntsRef;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OSMLayerParserTest {

  private IntEncodedValue ev;
  private OSMLayerParser parser;
  private EdgeIntAccess edgeIntAccess;

  @BeforeEach
  private void setUp() {
    ev = Layer.create();
    ev.init(new EncodedValue.InitializerConfig());
    parser = new OSMLayerParser(ev);
    edgeIntAccess = new ArrayEdgeIntAccess(1);
  }

  @Test
  void testParser() {
    int edgeId = 0;
    ReaderWay way = new ReaderWay(0);
    // Unset tag should be encoded as 0
    parser.handleWayTags(edgeId, edgeIntAccess, way, new IntsRef(2));
    assertEquals(0, ev.getInt(false, edgeId, edgeIntAccess));
    way.setTag("layer", "0");
    parser.handleWayTags(edgeId, edgeIntAccess, way, new IntsRef(2));
    assertEquals(0, ev.getInt(false, edgeId, edgeIntAccess));
    way.setTag("layer", "1");
    parser.handleWayTags(edgeId, edgeIntAccess, way, new IntsRef(2));
    assertEquals(1, ev.getInt(false, edgeId, edgeIntAccess));
    way.setTag("layer", "2");
    parser.handleWayTags(edgeId, edgeIntAccess, way, new IntsRef(2));
    assertEquals(2, ev.getInt(false, edgeId, edgeIntAccess));
    way.setTag("layer", "7");
    parser.handleWayTags(edgeId, edgeIntAccess, way, new IntsRef(2));
    assertEquals(7, ev.getInt(false, edgeId, edgeIntAccess));
    way.setTag("layer", "24");
    parser.handleWayTags(edgeId, edgeIntAccess, way, new IntsRef(2));
    assertEquals(8, ev.getInt(false, edgeId, edgeIntAccess));
    way.setTag("layer", "-1");
    parser.handleWayTags(edgeId, edgeIntAccess, way, new IntsRef(2));
    assertEquals(-1, ev.getInt(false, edgeId, edgeIntAccess));
    way.setTag("layer", "-5");
    parser.handleWayTags(edgeId, edgeIntAccess, way, new IntsRef(2));
    assertEquals(-5, ev.getInt(false, edgeId, edgeIntAccess));
    way.setTag("layer", "-7");
    parser.handleWayTags(edgeId, edgeIntAccess, way, new IntsRef(2));
    assertEquals(-7, ev.getInt(false, edgeId, edgeIntAccess));
  }
}