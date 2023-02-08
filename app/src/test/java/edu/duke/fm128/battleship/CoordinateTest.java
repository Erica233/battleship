package edu.duke.fm128.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CoordinateTest {
  @Test
  public void test_row_and_column() {
    Coordinate c1 = new Coordinate(1, 2);
    assertEquals(1, c1.getRow());
    assertEquals(2, c1.getColumn());
  }

  @Test
  public void test_equals() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(1, 2);
    Coordinate c3 = new Coordinate(1, 3);
    Coordinate c4 = new Coordinate(3, 2);
    assertEquals(c1, c1);   //equals should be reflexsive
    assertEquals(c1, c2);   //different objects bu same contents
    assertNotEquals(c1, c3);  //different contents
    assertNotEquals(c1, c4);
    assertNotEquals(c3, c4);
    assertNotEquals(c1, "(1, 2)"); //different types
  }

  @Test
  public void test_toString() {
    Coordinate c1 = new Coordinate(1, 2);
    
  }

}
