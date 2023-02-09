package edu.duke.fm128.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SimpleShipDisplayInfoTest {
  @Test
  public void test_data_and_hit() {
    Character d = 'd';
    Character o = 'o';
    SimpleShipDisplayInfo<Character> sd = new SimpleShipDisplayInfo<>(d, o);
    assertEquals(d, sd.myData);
    assertEquals(o, sd.onHit);
    assertNotEquals(d, sd.onHit);
  }

  @Test
  public void test_get_info() {
    Character d = 'd';
    Character o = 'o';
    SimpleShipDisplayInfo<Character> sd = new SimpleShipDisplayInfo<>(d, o);
    assertEquals(o, sd.getInfo(new Coordinate(0, 0), true));
    assertEquals(d, sd.getInfo(new Coordinate(0, 0), false));
    assertNotEquals(d, sd.getInfo(new Coordinate(0, 0), true));
    assertNotEquals(o, sd.getInfo(new Coordinate(0, 0), false));
  }

}
