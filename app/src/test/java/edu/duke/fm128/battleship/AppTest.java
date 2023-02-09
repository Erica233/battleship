/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.fm128.battleship;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
  @Test
  void test_read_placement() throws IOException {
    StringReader sr = new StringReader("B2V\nC8H\na4v\n");

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(bytes, true);

    Board<Character> b = new BattleShipBoard<>(10, 20);
    App app = new App(b, sr, ps);

    String prompt = "Please enter a location for a ship:";
    Placement[] expected = new Placement[3];
    expected[0] = new Placement(new Coordinate(1, 2), 'V');
    expected[1] = new Placement(new Coordinate(2, 8), 'H');
    expected[2] = new Placement(new Coordinate(0, 4), 'V');

    for (Placement placement : expected) {
      Placement p = app.readPlacement(prompt);
      assertEquals(p, placement); // did we get the right Placement back
      assertEquals(prompt + "\n", bytes.toString()); // should have printed prompt and newline
      bytes.reset(); // clear out bytes for next time around
    }
  }

  @Test
  void test_do_one_placement() throws IOException {
    StringReader sr = new StringReader("B2v\n");

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(bytes, true);

    Board<Character> b = new BattleShipBoard<>(4, 3);
    App app = new App(b, sr, ps);

    String prompt = "Where would you like to put your ship?";

    app.doOnePlacement();

    String expected = "  0|1|2|3\n" +
        "A  | | |  A\n" +
        "B  | |s|  B\n" +
        "C  | | |  C\n" +
        "  0|1|2|3\n";
    assertEquals(prompt + "\n" + expected, bytes.toString());
  }

  @Test
  @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
  void test_main() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);

    //get an InputStream for our input.txt file, and the expected output
    InputStream input = getClass().getClassLoader().getResourceAsStream("input.txt");
    assertNotNull(input);
    InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output.txt");
    assertNotNull(expectedStream);
    //remember the current System.in and System.out
    InputStream oldIn = System.in;
    PrintStream oldOut = System.out;

    //change to our new input and output
    try {
      System.setIn(input);
      System.setOut(out);
      App.main(new String[0]);
    }
    finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
    }

    String expected = new String(expectedStream.readAllBytes());
    String actual = bytes.toString();
    assertEquals(expected, actual);
  }
}
