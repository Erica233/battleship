package edu.duke.fm128.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class V1ShipFactoryTest {
  /**
   * Checks if the newly created ship is the same as expected
   * 
   * @param testShip       the ship to check
   * @param expectedName   the expected name of the ship
   * @param expectedLetter the expected letter
   * @param expectedLocs   the expected coordinates
   */
  private void checkShip(Ship<Character> testShip, String expectedName,
      char expectedLetter, Coordinate... expectedLocs) {
    assertEquals(expectedName, testShip.getName());
    for (Coordinate c : expectedLocs) {
      assertEquals(expectedLetter, testShip.getDisplayInfoAt(c));
    }
  }

  @Test
  void test_make_ships() {
    V1ShipFactory f = new V1ShipFactory();
    Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
    Placement h1_2 = new Placement(new Coordinate(1, 2), 'H');
    Placement p = new Placement(new Coordinate(1, 2), 'A');
    Ship<Character> sub = f.makeSubmarine(v1_2);
    checkShip(sub, "Submarine", 's', new Coordinate(1, 2), new Coordinate(2, 2));
    Ship<Character> sub2 = f.makeSubmarine(h1_2);
    checkShip(sub2, "Submarine", 's', new Coordinate(1, 2), new Coordinate(1, 3));

    Ship<Character> dst = f.makeDestroyer(v1_2);
    checkShip(dst, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2));
    Ship<Character> bat = f.makeBattleship(v1_2);
    checkShip(bat, "Battleship", 'b', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2),
        new Coordinate(4, 2));
    Ship<Character> crr = f.makeCarrier(v1_2);
    checkShip(crr, "Carrier", 'c', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2),
        new Coordinate(4, 2), new Coordinate(5, 2));

    assertThrows(IllegalArgumentException.class,
        () -> new V1ShipFactory().makeSubmarine(p));

  }
}
