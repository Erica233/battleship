package edu.duke.fm128.battleship;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class ZShapedShipTest {
    void helpTestZShapeShip(Character orientation, String[] ps) {
        ArrayList<Coordinate> realSet = ZShapedShip.makeCoords(new Coordinate(1, 0), orientation);
        ArrayList<Coordinate> expectedSet = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            expectedSet.add(new Coordinate(ps[i]));
        }
        assertEquals(expectedSet, realSet);
    }

    @Test
    void test_make_coords() {
        helpTestZShapeShip('U', new String[] {"b0", "c0", "d0", "e0", "d1", "e1", "f1"});
        helpTestZShapeShip('R', new String[] {"b4", "b3", "b2", "b1", "c2", "c1", "c0"});
        helpTestZShapeShip('D', new String[] {"f1", "e1", "d1", "c1", "d0", "c0", "b0"});
        helpTestZShapeShip('L', new String[] {"c0", "c1", "c2", "c3", "b2", "b3", "b4"});
    }
}