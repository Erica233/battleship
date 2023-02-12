package edu.duke.fm128.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class V2ShipFactoryTest {

  @Test
  void test_createTShapedShip() {
    V2ShipFactory f = new V2ShipFactory();
    Placement p1 = new Placement(new Coordinate(1, 2), 'a');
    assertThrows(IllegalArgumentException.class, () -> f.createTShapedShip(p1, 'b', "Battleship"));
  }

  @Test
  void test_createZShapedShip() {
    V2ShipFactory f = new V2ShipFactory();
    Placement p1 = new Placement(new Coordinate(1, 2), 'a');
    assertThrows(IllegalArgumentException.class, () -> f.createZShapedShip(p1, 'c', "Carrier"));
  }

  @Test
  void test_createRectangleShip() {
    V2ShipFactory f = new V2ShipFactory();
    Placement p1 = new Placement(new Coordinate(1, 2), 'a');
    Placement p2 = new Placement(new Coordinate(1, 2), 'H');
    assertThrows(IllegalArgumentException.class, () -> f.createRectangleShip(p1, 10, 20, 's', "Submarine"));
    Ship<Character> s2 = f.createRectangleShip(p2, 10, 20, 's', "Submarine");
  }
}
