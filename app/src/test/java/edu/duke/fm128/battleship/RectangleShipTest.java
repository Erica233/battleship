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

  @Test
  void test_record_and_was_hit_at() {
    Coordinate c0 = new Coordinate(0, 0);
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(2, 2);
    Coordinate c3 = new Coordinate(3, 2);
    RectangleShip<Character> recShip = new RectangleShip<>(c1, 1, 3, 's', '*');
    assertThrows(IllegalArgumentException.class, () -> new RectangleShip<>(new Coordinate(1, 3), 1, 3, 's', '*').wasHitAt(c0));
    assertFalse(recShip.wasHitAt(c1));
    assertFalse(recShip.wasHitAt(c2));
    assertFalse(recShip.wasHitAt(c3));
    recShip.recordHitAt(c2);
    assertFalse(recShip.wasHitAt(c1));
    assertTrue(recShip.wasHitAt(c2));
    assertFalse(recShip.wasHitAt(c3));
    assertThrows(IllegalArgumentException.class, () -> new RectangleShip<>(new Coordinate(1, 3), 1, 3, 's', '*').wasHitAt(c0));
  }

  @Test
  void test_is_sunk() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(2, 2);
    RectangleShip<Character> recShip = new RectangleShip<>(c1, 1, 2, 's', '*');
    assertFalse(recShip.isSunk());
    recShip.recordHitAt(c1);
    assertFalse(recShip.isSunk());
    recShip.recordHitAt(c2);
    assertTrue(recShip.isSunk());
  }

  @Test
  void test_get_display_info_at() {
    Coordinate c0 = new Coordinate(0, 0);
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(2, 2);
    Character data = 's';
    Character onHit = '*';
    RectangleShip<Character> recShip = new RectangleShip<>(c1, 1, 2, 's', '*');
    assertThrows(IllegalArgumentException.class, () -> new RectangleShip<>(new Coordinate(1, 2), 1, 2, 's', '*').getDisplayInfoAt(c0));
    assertEquals(data, recShip.getDisplayInfoAt(c1));
    assertEquals(data, recShip.getDisplayInfoAt(c2));
    recShip.recordHitAt(c1);
    assertThrows(IllegalArgumentException.class, () -> new RectangleShip<>(new Coordinate(1, 2), 1, 2, 's', '*').getDisplayInfoAt(c0));
    assertEquals(onHit, recShip.getDisplayInfoAt(c1));
    assertEquals(data, recShip.getDisplayInfoAt(c2));
    recShip.recordHitAt(c2);
    assertThrows(IllegalArgumentException.class, () -> new RectangleShip<>(new Coordinate(1, 2), 1, 2, 's', '*').getDisplayInfoAt(c0));
    assertEquals(onHit, recShip.getDisplayInfoAt(c1));
    assertEquals(onHit, recShip.getDisplayInfoAt(c2));
  }
}
