package edu.duke.fm128.battleship;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class RectangleShipTest {
  @Test
  void test_make_coords() {
    HashSet<Coordinate> realSet = RectangleShip.makeCoords(new Coordinate(1, 2), 1, 3);
    HashSet<Coordinate> expectedSet = new HashSet<>();
    expectedSet.add(new Coordinate(1, 2));
    expectedSet.add(new Coordinate(2, 2));
    expectedSet.add(new Coordinate(3, 2));
    assertEquals(expectedSet, realSet);
  }

  @Test
  void test_occupy() {
    RectangleShip<Character> recShip = new RectangleShip<>(new Coordinate(1, 2), 1, 3, 's', '*');
    assertTrue(recShip.occupiesCoordinates(new Coordinate(1, 2)));
    assertTrue(recShip.occupiesCoordinates(new Coordinate(2, 2)));
    assertTrue(recShip.occupiesCoordinates(new Coordinate(3, 2)));
    assertFalse(recShip.occupiesCoordinates(new Coordinate(0, 0)));
  }
}
