package edu.duke.fm128.battleship;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class TextPlayerTest {
  /**
   * A helper function to create a TextPlayer
   *
   * @param w         width
   * @param h         height
   * @param inputData inputs
   * @param bytes     outputs
   * @return a newly created TextPlayer
   */
  private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<>(w, h);
    V1ShipFactory shipFactory = new V1ShipFactory();
    return new TextPlayer("A", board, input, output, shipFactory);
  }

  @Test
  void test_read_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "B2V\nC8H\na4v\n", bytes);

    String prompt = "Please enter a location for a ship:";
    Placement[] expected = new Placement[3];
    expected[0] = new Placement(new Coordinate(1, 2), 'V');
    expected[1] = new Placement(new Coordinate(2, 8), 'H');
    expected[2] = new Placement(new Coordinate(0, 4), 'V');

    for (Placement placement : expected) {
      Placement p = player.readPlacement(prompt);
      assertEquals(p, placement); // did we get the right Placement back
      assertEquals(prompt + "\n", bytes.toString()); // should have printed prompt and newline
      bytes.reset(); // clear out bytes for next time around
    }
  }

  @Test
  void test_do_one_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(4, 3, "A2v\n", bytes);

    String prompt = "Player A where do you want to place a Destroyer?";

    player.doOnePlacement("Destroyer", player.shipCreationFns.get("Destroyer"));

    String expected = "  0|1|2|3\n" +
        "A  | |d|  A\n" +
        "B  | |d|  B\n" +
        "C  | |d|  C\n" +
        "  0|1|2|3\n";
    assertEquals(prompt + "\n" + expected, bytes.toString());
  }

  @Test
  void test_doPlacementPhase() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "B2V\n", bytes);
    player.doPlacementPhase();

    String emptyBoard = "---------------------------------------------------------------------------\n" +
        "  0|1|2|3|4|5|6|7|8|9\n" +
        "A  | | | | | | | | |  A\n" +
        "B  | | | | | | | | |  B\n" +
        "C  | | | | | | | | |  C\n" +
        "D  | | | | | | | | |  D\n" +
        "E  | | | | | | | | |  E\n" +
        "F  | | | | | | | | |  F\n" +
        "G  | | | | | | | | |  G\n" +
        "H  | | | | | | | | |  H\n" +
        "I  | | | | | | | | |  I\n" +
        "J  | | | | | | | | |  J\n" +
        "K  | | | | | | | | |  K\n" +
        "L  | | | | | | | | |  L\n" +
        "M  | | | | | | | | |  M\n" +
        "N  | | | | | | | | |  N\n" +
        "O  | | | | | | | | |  O\n" +
        "P  | | | | | | | | |  P\n" +
        "Q  | | | | | | | | |  Q\n" +
        "R  | | | | | | | | |  R\n" +
        "S  | | | | | | | | |  S\n" +
        "T  | | | | | | | | |  T\n" +
        "  0|1|2|3|4|5|6|7|8|9\n";

    String instructions = "---------------------------------------------------------------------------\n" +
        "Player A: you are going to place the following ships (which are all\n" +
        "rectangular). For each ship, type the coordinate of the upper left\n" +
        "side of the ship, followed by either H (for horizontal) or V (for\n" +
        "vertical).  For example M4H would place a ship horizontally starting\n" +
        "at M4 and going to the right.  You have\n" +
        "\n" +
        "2 \"Submarines\" ships that are 1x2 \n" +
        "3 \"Destroyers\" that are 1x3\n" +
        "3 \"Battleships\" that are 1x4\n" +
        "2 \"Carriers\" that are 1x6\n" +
        "---------------------------------------------------------------------------\n";
    String prompt = "Player A where do you want to place a Destroyer?";

    String afterPlace = "  0|1|2|3|4|5|6|7|8|9\n" +
        "A  | | | | | | | | |  A\n" +
        "B  | |d| | | | | | |  B\n" +
        "C  | |d| | | | | | |  C\n" +
        "D  | |d| | | | | | |  D\n" +
        "E  | | | | | | | | |  E\n" +
        "F  | | | | | | | | |  F\n" +
        "G  | | | | | | | | |  G\n" +
        "H  | | | | | | | | |  H\n" +
        "I  | | | | | | | | |  I\n" +
        "J  | | | | | | | | |  J\n" +
        "K  | | | | | | | | |  K\n" +
        "L  | | | | | | | | |  L\n" +
        "M  | | | | | | | | |  M\n" +
        "N  | | | | | | | | |  N\n" +
        "O  | | | | | | | | |  O\n" +
        "P  | | | | | | | | |  P\n" +
        "Q  | | | | | | | | |  Q\n" +
        "R  | | | | | | | | |  R\n" +
        "S  | | | | | | | | |  S\n" +
        "T  | | | | | | | | |  T\n" +
        "  0|1|2|3|4|5|6|7|8|9\n";

    assertEquals(emptyBoard + instructions + prompt + "\n" + afterPlace, bytes.toString());
  }
}
