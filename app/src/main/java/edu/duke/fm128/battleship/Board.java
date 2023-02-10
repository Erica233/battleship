package edu.duke.fm128.battleship;

/**
 * A Board
 */
public interface Board<T> {
  /**
   * search for any ship that occupies coordinate c.
   * If one is found, that Ship is "hit" by the attack and should record it, then return this ship.
   * If no ships are at this coordinate, record the miss in the enemyMisses, and return null.
   *
   * @param c coordinate which the enemy tries to fire at
   * @return the ship that is fired at
   */
  Ship<T> fireAt(Coordinate c);

  /**
   * Adds the ship to the list of myShips and return true
   * 
   * @param toAdd the ship to add
   * @return a String that shows the validity of the add operation
   */
  String tryAddShip(Ship<T> toAdd);

  /**
   * Gives the information of the given coordinate if it is occupied.
   * 
   * @param where the coordinate
   * @return the information of the given coordinate if it is occupied, otherwise,
   *         return null
   */
  T whatIsAtForSelf(Coordinate where);

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
