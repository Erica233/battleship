package edu.duke.fm128.battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * A Board
 */
public interface Board<T> {
  /**
   * search for any ship that occupies coordinate c.
   * If one is found, that Ship is "hit" by the attack and should record it, then
   * return this ship.
   * If no ships are at this coordinate, record the miss in the enemyMisses, and
   * return null.
   *
   * @param c coordinate which the enemy tries to fire at
   * @return the ship that is fired at
   */
  Ship<T> fireAt(Coordinate c);

  /**
   * do the sonar scan given the center coordinate and ship names to find,
   * and generate the results in a map, with shipName as key,
   * and the number of ship as value
   *
   * @param c         center coordinate
   * @param shipNames ship names
   * @return a hashmap about how many ships of each type within the area
   */
  HashMap<String, Integer> scanAt(Coordinate c, Set<String> shipNames);

  /**
   * Adds the ship to the list of myShips and return true
   * 
   * @param toAdd the ship to add
   * @return a String that shows the validity of the add operation
   */
  String tryAddShip(Ship<T> toAdd);

  /**
   * removes the ship within board,
   * notice: the input must be a ship within board
   *
   * @param toRemove the ship to remove
   */
  void removeShip(Ship<T> toRemove);

  /**
   * substitute the old ship with the new ship in the board
   *
   * @param oldShip the old ship
   * @param newShip the new ship
   */
  void substituteShip(Ship<Character> oldShip, Ship<Character> newShip);

  /**
   * Gives the information of the given coordinate if it is occupied.
   * 
   * @param where the coordinate
   * @return the information of the given coordinate if it is occupied, otherwise,
   *         return null
   */
  T whatIsAtForSelf(Coordinate where);

  /**
   * Gives the information of the given coordinate if it is occupied to enemy.
   *
   * @param where the coordinate
   * @return the information of the given coordinate if it is occupied, otherwise,
   *         return null
   */
  T whatIsAtForEnemy(Coordinate where);

  /**
   * checks if all myShips have sunk
   *
   * @return true if my ships are all sunk, otherwise false
   */
  boolean allSunk();

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

  /**
   * checks if the input coordinate is within the board's boundary
   *
   * @param c coordinate
   * @return true if coordinates is within the board, otherwise false
   */
  boolean checkContain(Coordinate c);

  /**
   * find the ship that the input coordinate belongs to
   *
   * @param c the coordinate to check if it is part of one of my ships
   * @return ship if the coordinate is part of one of my ships, otherwise return
   *         null
   */
  Ship<T> findShip(Coordinate c);
}
