package edu.duke.fm128.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody) {
    Board<Character> b1 = new BattleShipBoard<>(w, h, 'X');
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
    Board<Character> wideBoard = new BattleShipBoard<>(11, 20, 'X');
    Board<Character> tallBoard = new BattleShipBoard<>(10, 27, 'X');
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

    Board<Character> b1 = new BattleShipBoard<>(4, 3, 'X');
    b1.tryAddShip(new RectangleShip<>(new Coordinate(0, 0), 's', '*'));
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expected, view.displayMyOwnBoard());

    b1.tryAddShip(new RectangleShip<>(new Coordinate(2, 3), 's', '*'));
    expectedBody = "A s| | |  A\n" +
        "B  | | |  B\n" +
        "C  | | |s C\n";
    expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_displayEnemyBoard() {
    String myView = "  0|1|2|3\n" +
        "A  | | |  A\n" +
        "B  |s|s|  B\n" +
        "C  | | |  C\n" +
        "  0|1|2|3\n";
    String enemyView = "  0|1|2|3\n" +
        "A  | | |  A\n" +
        "B  | | |  B\n" +
        "C  | | |  C\n" +
        "  0|1|2|3\n";
    Board<Character> b1 = new BattleShipBoard<>(4, 3, 'X');
    V1ShipFactory f = new V1ShipFactory();
    Ship<Character> s = f.makeSubmarine(new Placement(new Coordinate(1, 1), 'H'));
    b1.tryAddShip(s);
    BoardTextView view = new BoardTextView(b1);
    assertEquals(myView, view.displayMyOwnBoard());
    assertEquals(enemyView, view.displayEnemyBoard());

    b1.fireAt(new Coordinate(0, 0));
    b1.fireAt(new Coordinate(1, 2));
    String enemyView1 = "  0|1|2|3\n" +
        "A X| | |  A\n" +
        "B  | |s|  B\n" +
        "C  | | |  C\n" +
        "  0|1|2|3\n";
    assertEquals(enemyView1, view.displayEnemyBoard());
  }

  @Test
  public void test_displayMyBoardWithEnemyNextToIt() {
    Board<Character> b1 = new BattleShipBoard<>(10, 20, 'X');
    Board<Character> b2 = new BattleShipBoard<>(10, 20, 'X');
    BoardTextView v1 = new BoardTextView(b1);
    BoardTextView v2 = new BoardTextView(b2);
    String expected = "     Your ocean                           Player B's ocean\n" +
        "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n" +
        "A  | | | | | | | | |  A                A  | | | | | | | | |  A\n" +
        "B  | | | | | | | | |  B                B  | | | | | | | | |  B\n" +
        "C  | | | | | | | | |  C                C  | | | | | | | | |  C\n" +
        "D  | | | | | | | | |  D                D  | | | | | | | | |  D\n" +
        "E  | | | | | | | | |  E                E  | | | | | | | | |  E\n" +
        "F  | | | | | | | | |  F                F  | | | | | | | | |  F\n" +
        "G  | | | | | | | | |  G                G  | | | | | | | | |  G\n" +
        "H  | | | | | | | | |  H                H  | | | | | | | | |  H\n" +
        "I  | | | | | | | | |  I                I  | | | | | | | | |  I\n" +
        "J  | | | | | | | | |  J                J  | | | | | | | | |  J\n" +
        "K  | | | | | | | | |  K                K  | | | | | | | | |  K\n" +
        "L  | | | | | | | | |  L                L  | | | | | | | | |  L\n" +
        "M  | | | | | | | | |  M                M  | | | | | | | | |  M\n" +
        "N  | | | | | | | | |  N                N  | | | | | | | | |  N\n" +
        "O  | | | | | | | | |  O                O  | | | | | | | | |  O\n" +
        "P  | | | | | | | | |  P                P  | | | | | | | | |  P\n" +
        "Q  | | | | | | | | |  Q                Q  | | | | | | | | |  Q\n" +
        "R  | | | | | | | | |  R                R  | | | | | | | | |  R\n" +
        "S  | | | | | | | | |  S                S  | | | | | | | | |  S\n" +
        "T  | | | | | | | | |  T                T  | | | | | | | | |  T\n" +
        "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n";
    assertEquals(expected, v1.displayMyBoardWithEnemyNextToIt(v2, "Your ocean", "Player B's ocean"));
  }
}
