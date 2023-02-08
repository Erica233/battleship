package edu.duke.fm128.battleship;

/**
 * A Board
 */
public interface Board<T> {
  /**
   * Adds the ship to the list of myShips and return true
   * 
   * @param toAdd the ship to add
   * @return true
   */
  boolean tryAddShip(Ship<T> toAdd);

  /**
   * Gives the information of the given coordinate if it is occupied.
   * 
   * @param where the coordinate
   * @return the information of the given coordinate if it is occupied, otherwise,
   *         return null
   */
  T whatIsAt(Coordinate where);

  /**
   * Gets the width of the Board
   * 
   * @return the width of the Board
   */
  int getWidth();

  /**
   * Gets the height of the Board
   * 
   * @return the height of the Board
   */
  int getHeight();
}
