package edu.duke.fm128.battleship;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class TShapedShipTest {
    void helpTestTShapeShip(Character orientation, String[] ps) {
        HashSet<Coordinate> realSet = TShapedShip.makeCoords(new Coordinate(1, 0), orientation);
        HashSet<Coordinate> expectedSet = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            expectedSet.add(new Coordinate(ps[i]));
        }
        assertEquals(expectedSet, realSet);
    }

    @Test
    void test_make_coords() {
        helpTestTShapeShip('U', new String[] {"c0", "c1", "c2", "b1"});
        helpTestTShapeShip('R', new String[] {"b0", "c0", "d0", "c1"});
        helpTestTShapeShip('D', new String[] {"b0", "b1", "b2", "c1"});
        helpTestTShapeShip('L', new String[] {"c0", "b1", "c1", "d1"});
    }

    @Test
    void test_get_name() {
//        Coordinate c1 = new Coordinate(1, 2);
//        String name = "Battleship";
//        RectangleShip<Character> recShip = new RectangleShip<>(name, c1, 1, 2, 's', '*');
//        assertEquals("submarine", recShip.getName());
//        assertNotEquals("testship", recShip.getName());
    }
}