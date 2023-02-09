package edu.duke.fm128.battleship;

/**
 * This interface represents any type of ShipDisplayInfo
 *
 * @param <T> the type of information the view needs to display the ship
 */
public interface ShipDisplayInfo<T> {
  /**
   * Checks if (hit) and returns onHit if so, and myData otherwise.
   *
   * @param where the coordinate
   * @param hit the ship is hit or not
   * @return the onHit if hit, otherwise myData
   */
   T getInfo(Coordinate where, boolean hit);
}
