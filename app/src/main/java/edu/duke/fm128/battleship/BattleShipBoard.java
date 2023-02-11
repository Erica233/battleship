package edu.duke.fm128.battleship;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * A battleship board for the battleship game
 */
public class BattleShipBoard<T> implements Board<T> {
  private final int width;
  private final int height;
  private final ArrayList<Ship<T>> myShips;
  private final PlacementRuleChecker<T> placementChecker;
  private final HashSet<Coordinate> enemyMisses;
  private final T missInfo;

  /**
   * Constructs a BattleShipBoard with the specified width and height
   *
   * @param w is the width of the newly constructed board.
   * @param h is the height of the newly constructed board.
   */
  public BattleShipBoard(int w, int h, T _missInfo) {
    this(w, h, new InBoundsRuleChecker<>(null), _missInfo);
  }

  /**
   * Constructs a BattleShipBoard with the specified width and height and
   * PlacementRuleChecker
   * 
   * @param w   is the width of the newly constructed board.
   * @param h   is the height of the newly constructed board.
   * @param prc is the PlacementRuleChecker
   * @throws IllegalArgumentException if the width or height are less than or
   *                                  equal to zero.
   */
  public BattleShipBoard(int w, int h, PlacementRuleChecker<T> prc, T _missInfo) {
    if (w <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
    }
    if (h <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
    }
    this.width = w;
    this.height = h;
    this.myShips = new ArrayList<>();
    this.placementChecker = prc;
    this.enemyMisses = new HashSet<>();
    this.missInfo = _missInfo;
  }

  /**
   * checks if all myShips have sunk
   *
   * @return true if my ships are all sunk, otherwise false
   */
  @Override
  public boolean allSunk() {
    for (Ship<T> s : myShips) {
      if (!s.isSunk()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public Ship<T> fireAt(Coordinate c) {
    // search for any ship that occupies coordinate c
    for (Ship<T> ship : myShips) {
      // If one is found, that Ship is "hit" by the attack and should record it
      if (ship.occupiesCoordinates(c)) {
        ship.recordHitAt(c);
        return ship;
      }
    }
    // If no ships are at this coordinate, record the miss in the enemyMisses, and
    // return null.
    enemyMisses.add(c);
    return null;
  }

  /**
   * Checks the validity of the placement,
   * if it is valid, adds a ship to the list of myShips and returns true,
   * otherwise, return false
   * 
   * @param toAdd the ship to add
   * @return null if the placement is valid, otherwise return exception
   *         information
   */
  @Override
  public String tryAddShip(Ship<T> toAdd) {
    String info = placementChecker.checkPlacement(toAdd, this);
    if (info == null) {
      myShips.add(toAdd);
      return null;
    }
    return info;
  }

  @Override
  public T whatIsAtForSelf(Coordinate where) {
    return whatIsAt(where, true);
  }

  @Override
  public T whatIsAtForEnemy(Coordinate where) {
    return whatIsAt(where, false);
  }

  /**
   * Gives the information offered by the Ship which occupies the given
   * coordinate.
   *
   * @param where  the coordinate
   * @param isSelf the boolean if it is for self
   * @return the information offered by the ship which occupies the given
   *         coordinate, or return null if it is not occupied by any ships
   */
  protected T whatIsAt(Coordinate where, boolean isSelf) {
    // if the specified coordinate cooresponds to a ship,
    // use its display info.
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(where)) {
        return s.getDisplayInfoAt(where, isSelf);
      }
    }
    // However, if it does not, and we are doing this for an enemy board,
    // then check for a miss before return null.
    if (!isSelf && enemyMisses.contains(where)) {
      return missInfo;
    }
    return null;
  }

  /**
   * Gets the height of the BattleShipBoard
   * 
   * @return the height of the BattleShipBoard
   */
  public int getHeight() {
    return height;
  }

  /**
   * Gets the width of the BattleShipBoard
   * 
   * @return the width of the BattleShipBoard
   */
  public int getWidth() {
    return width;
  }
}
