package edu.duke.fm128.battleship;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<>(10, 20, 'X');
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<>(10, 0, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<>(0, 20, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<>(10, -5, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<>(-8, 20, 'X'));
  }

  /**
   * A helper function to check whether the board information is the same as
   * expected
   *
   * @param b        current BattleShipBoard
   * @param expected expected results
   * @param <T>      Character
   */
  private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expected) {
    for (int i = 0; i < b.getWidth(); i++) {
      for (int j = 0; j < b.getHeight(); j++) {
        assertEquals(expected[i][j], b.whatIsAtForSelf(new Coordinate(i, j)));
      }
    }
  }

  @Test
  public void test_whatIsAt() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<>(10, 20, 'X');
    Character[][] expected = new Character[10][20];
    // check that it has no ships anywhere
    checkWhatIsAtBoard(b1, expected);

    // add ships and test reults
    b1.tryAddShip(new RectangleShip<>(new Coordinate(3, 5), 's', '*'));
    expected[3][5] = 's';
    checkWhatIsAtBoard(b1, expected);
    b1.tryAddShip(new RectangleShip<>(new Coordinate(0, 0), 's', '*'));
    expected[0][0] = 's';
    checkWhatIsAtBoard(b1, expected);
  }

  @Test
  public void test_whatIsAtForEnemy() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<>(10, 20, 'X');
    V1ShipFactory f = new V1ShipFactory();
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(0, 0);
    Placement p1 = new Placement(c1, 'V');
    f.makeSubmarine(p1);

    // add ships and test reults
    b1.tryAddShip(new RectangleShip<>(c1, 's', '*'));
    b1.fireAt(c1);
    b1.fireAt(c2);
    assertEquals('s', b1.whatIsAtForEnemy(c1));
    assertEquals('X', b1.whatIsAtForEnemy(c2));
  }

  @Test
  void test_try_add_ship() {
    InBoundsRuleChecker<Character> checker = new InBoundsRuleChecker<>(new NoCollisionRuleChecker<>(null));
    BattleShipBoard<Character> b1 = new BattleShipBoard<>(10, 20, checker, 'X');
    V1ShipFactory f = new V1ShipFactory();
    Placement p0 = new Placement(new Coordinate(1, 2), 'H');
    Placement p1 = new Placement(new Coordinate(-1, 2), 'H');
    Ship<Character> sub0 = f.makeSubmarine(p0);
    Ship<Character> sub1 = f.makeSubmarine(p1);
    assertNull(b1.tryAddShip(sub0));
    assertNotEquals(null, b1.tryAddShip(sub1));
    Placement p2 = new Placement(new Coordinate(19, 0), 'V');
    Ship<Character> sub2 = f.makeSubmarine(p2);
    assertNotEquals(null, b1.tryAddShip(sub2));
  }

  @Test
  void test_fire_at() {
    InBoundsRuleChecker<Character> checker = new InBoundsRuleChecker<>(new NoCollisionRuleChecker<>(null));
    BattleShipBoard<Character> b1 = new BattleShipBoard<>(3, 2, checker, 'X');
    V1ShipFactory f = new V1ShipFactory();
    Coordinate c0 = new Coordinate(0, 0);
    Coordinate c1 = new Coordinate(0, 1);
    Coordinate c2 = new Coordinate(0, 2);
    Placement p0 = new Placement(new Coordinate(0, 0), 'H');
    Ship<Character> sub0 = f.makeSubmarine(p0);
    b1.tryAddShip(sub0);

    assertSame(b1.fireAt(c0), sub0);
    assertFalse(sub0.isSunk());
    assertSame(b1.fireAt(c1), sub0);
    assertTrue(sub0.isSunk());
    assertNull(b1.fireAt(c2));
  }

  @Test
  void test_allSunk() {
    InBoundsRuleChecker<Character> checker = new InBoundsRuleChecker<>(new NoCollisionRuleChecker<>(null));
    BattleShipBoard<Character> b1 = new BattleShipBoard<>(3, 2, checker, 'X');
    V1ShipFactory f = new V1ShipFactory();
    Coordinate c0 = new Coordinate(0, 0);
    Coordinate c1 = new Coordinate(0, 1);
    Placement p0 = new Placement(new Coordinate(0, 0), 'H');
    Ship<Character> sub0 = f.makeSubmarine(p0);
    b1.tryAddShip(sub0);
    assertFalse(b1.allSunk());
    b1.fireAt(c0);
    assertFalse(b1.allSunk());
    b1.fireAt(c1);
    assertTrue(b1.allSunk());
  }

  @Test
  void test_not_findShip() {
    InBoundsRuleChecker<Character> checker = new InBoundsRuleChecker<>(new NoCollisionRuleChecker<>(null));
    BattleShipBoard<Character> b1 = new BattleShipBoard<>(3, 2, checker, 'X');
    V2ShipFactory f = new V2ShipFactory();
    assertNull(b1.findShip(new Coordinate(0, 0)));
  }

}
