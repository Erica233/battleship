package edu.duke.fm128.battleship;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * A battleship board for the battleship game
 */
public class BattleShipBoard<T> implements Board<T> {
  private final int width;
  private final int height;
  final ArrayList<Ship<T>> myShips;
  private final PlacementRuleChecker<T> placementChecker;
  HashSet<Coordinate> enemyMisses;

  /**
   * Constructs a BattleShipBoard with the specified width and height
   *
   * @param w is the width of the newly constructed board.
   * @param h is the height of the newly constructed board.
   */
  public BattleShipBoard(int w, int h) {
    this(w, h, new InBoundsRuleChecker<>(null));
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
  public BattleShipBoard(int w, int h, PlacementRuleChecker<T> prc) {
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
  }

  @Override
  public Ship<T> fireAt(Coordinate c) {
    //search for any ship that occupies coordinate c
    for (Ship<T> ship: myShips) {
      //If one is found, that Ship is "hit" by the attack and should record it
      if (ship.occupiesCoordinates(c)) {
        ship.recordHitAt(c);
        return ship;
      }
    }
    //If no ships are at this coordinate, record the miss in the enemyMisses, and return null.
    enemyMisses.add(c);
    return null;
  }

  /**
   * Checks the validity of the placement,
   * if it is valid, adds a ship to the list of myShips and returns true,
   * otherwise, return false
   * 
   * @param toAdd the ship to add
   * @return true if the placement is valid, otherwise false
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

  /**
   * Gives the information offered by the Ship which occupies the given
   * coordinate.
   * 
   * @param where the coordinate
   * @return the information offered by the ship which occupies the given
   *         coordinate, or return null if it is not occupied by any ships
   */
  @Override
  public T whatIsAtForSelf(Coordinate where) {
    return whatIsAt(where, true);
  }

  protected T whatIsAt(Coordinate where, boolean isSelf) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(where)) {
        return s.getDisplayInfoAt(where, isSelf);
      }
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
