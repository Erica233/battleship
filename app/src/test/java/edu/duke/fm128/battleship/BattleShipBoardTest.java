package edu.duke.fm128.battleship;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<>(10, 20);
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<>(10, 0));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<>(0, 20));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<>(10, -5));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<>(-8, 20));
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
        assertEquals(expected[i][j], b.whatIsAt(new Coordinate(i, j)));
      }
    }
  }

  @Test
  public void test_whatIsAt() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<>(10, 20);
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
}
