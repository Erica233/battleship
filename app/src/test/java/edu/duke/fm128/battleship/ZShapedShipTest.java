package edu.duke.fm128.battleship;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class ZShapedShipTest {
    void helpTestZShapeShip(Character orientation, String[] ps) {
        HashSet<Coordinate> realSet = ZShapedShip.makeCoords(new Coordinate(1, 0), orientation);
        HashSet<Coordinate> expectedSet = new HashSet<>();
        for (int i = 0; i < 7; i++) {
            expectedSet.add(new Coordinate(ps[i]));
        }
        assertEquals(expectedSet, realSet);
    }

    @Test
    void test_make_coords() {
        helpTestZShapeShip('U', new String[] {"b0", "c0", "d0", "e0", "d1", "e1", "f1"});
        helpTestZShapeShip('R', new String[] {"b1", "b2", "b3", "b4", "c0", "c1", "c2"});
        helpTestZShapeShip('D', new String[] {"b0", "c0", "d0", "c1", "d1", "e1", "f1"});
        helpTestZShapeShip('L', new String[] {"b2", "b3", "b4", "c0", "c1", "c2", "c3"});
    }
}