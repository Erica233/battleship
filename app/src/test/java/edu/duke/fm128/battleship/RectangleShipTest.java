package edu.duke.fm128.battleship;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RectangleShipTest {
  @Test
  void test_get_coordinates() {
    HashSet<Coordinate> expected = new HashSet<>();
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(2, 2);
    Coordinate c3 = new Coordinate(3, 2);
    expected.add(c1);
    expected.add(c2);
    expected.add(c3);
    RectangleShip<Character> recShip = new RectangleShip<>("submarine", c1, 1, 3, 's', '*');
    assertEquals(expected, recShip.getCoordinates());
  }

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
    RectangleShip<Character> recShip = new RectangleShip<>("submarine", new Coordinate(1, 2), 1, 3, 's', '*');
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
    RectangleShip<Character> recShip = new RectangleShip<>("submarine", c1, 1, 3, 's', '*');
    assertThrows(IllegalArgumentException.class,
        () -> new RectangleShip<>("submarine", new Coordinate(1, 3), 1, 3, 's', '*').wasHitAt(c0));
    assertFalse(recShip.wasHitAt(c1));
    assertFalse(recShip.wasHitAt(c2));
    assertFalse(recShip.wasHitAt(c3));
    recShip.recordHitAt(c2);
    assertFalse(recShip.wasHitAt(c1));
    assertTrue(recShip.wasHitAt(c2));
    assertFalse(recShip.wasHitAt(c3));
    assertThrows(IllegalArgumentException.class,
        () -> new RectangleShip<>("submarine", new Coordinate(1, 3), 1, 3, 's', '*').wasHitAt(c0));
  }

  @Test
  void test_is_sunk() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(2, 2);
    RectangleShip<Character> recShip = new RectangleShip<>("submarine", c1, 1, 2, 's', '*');
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
    RectangleShip<Character> recShip = new RectangleShip<>("submarine", c1, 1, 2, 's', '*');
    assertThrows(IllegalArgumentException.class,
        () -> new RectangleShip<>("submarine", new Coordinate(1, 2), 1, 2, 's', '*').getDisplayInfoAt(c0, true));
    assertEquals(data, recShip.getDisplayInfoAt(c1, true));
    assertEquals(data, recShip.getDisplayInfoAt(c2, true));
    recShip.recordHitAt(c1);
    assertThrows(IllegalArgumentException.class,
        () -> new RectangleShip<>("submarine", new Coordinate(1, 2), 1, 2, 's', '*').getDisplayInfoAt(c0, true));
    assertEquals(onHit, recShip.getDisplayInfoAt(c1, true));
    assertEquals(data, recShip.getDisplayInfoAt(c2, true));
    recShip.recordHitAt(c2);
    assertThrows(IllegalArgumentException.class,
        () -> new RectangleShip<>("submarine", new Coordinate(1, 2), 1, 2, 's', '*').getDisplayInfoAt(c0, true));
    assertEquals(onHit, recShip.getDisplayInfoAt(c1, true));
    assertEquals(onHit, recShip.getDisplayInfoAt(c2, true));

    RectangleShip<Character> ship1 = new RectangleShip<>("submarine", c1, 1, 2,
        new SimpleShipDisplayInfo<>(data, onHit), new SimpleShipDisplayInfo<>(' ', data));
    assertEquals(' ', ship1.getDisplayInfoAt(c1, false));
    assertEquals(' ', ship1.getDisplayInfoAt(c2, false));
    ship1.recordHitAt(c2);
    assertEquals(' ', ship1.getDisplayInfoAt(c1, false));
    assertEquals(data, ship1.getDisplayInfoAt(c2, false));
  }

  @Test
  void test_get_name() {
    Coordinate c1 = new Coordinate(1, 2);
    String name = "submarine";
    RectangleShip<Character> recShip = new RectangleShip<>(name, c1, 1, 2, 's', '*');
    assertEquals("submarine", recShip.getName());
    assertNotEquals("testship", recShip.getName());
  }
}
