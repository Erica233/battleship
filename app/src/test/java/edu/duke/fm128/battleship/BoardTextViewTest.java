package edu.duke.fm128.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody) {
    Board<Character> b1 = new BattleShipBoard<>(w, h);
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_display_empty_2by2() {
    String expectedHeader = "  0|1\n";
    String expectedBody = "A  |  A\n" +
        "B  |  B\n";
    emptyBoardHelper(2, 2, expectedHeader, expectedBody);
  }

  @Test
  public void test_display_empty_3by2() {
    String expectedHeader = "  0|1|2\n";
    String expectedBody = "A  | |  A\n" +
        "B  | |  B\n";
    emptyBoardHelper(3, 2, expectedHeader, expectedBody);
  }

  @Test
  public void test_display_empty_3by5() {
    String expectedHeader = "  0|1|2\n";
    String expectedBody = "A  | |  A\n" +
        "B  | |  B\n" +
        "C  | |  C\n" +
        "D  | |  D\n" +
        "E  | |  E\n";
    emptyBoardHelper(3, 5, expectedHeader, expectedBody);
  }

  @Test
  public void test_invalid_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<>(11, 20);
    Board<Character> tallBoard = new BattleShipBoard<>(10, 27);
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
  }

  @Test
  public void test_display_occupied_4by3() {
    String expectedHeader = "  0|1|2|3\n";
    String expectedBody = "A s| | |  A\n" +
            "B  | | |  B\n" +
            "C  | | |  C\n";
    String expected = expectedHeader + expectedBody + expectedHeader;

    Board<Character> b1 = new BattleShipBoard<>(4, 3);
    b1.tryAddShip(new BasicShip(new Coordinate(0, 0)));
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expected, view.displayMyOwnBoard());

    b1.tryAddShip(new BasicShip(new Coordinate(2, 3)));
    expectedBody = "A s| | |  A\n" +
            "B  | | |  B\n" +
            "C  | | |s C\n";
    expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }
}
