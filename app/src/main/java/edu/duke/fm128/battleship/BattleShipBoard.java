package edu.duke.fm128.battleship;

import java.util.ArrayList;

/**
 * A battleship board for the battleship game
 */
public class BattleShipBoard<T> implements Board<T> {
  private final int width;
  private final int height;
  final ArrayList<Ship<T>> myShips;
  private final PlacementRuleChecker<T> placementChecker;

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
   * Constructs a BattleShipBoard with the specified width and height and PlacementRuleChecker
   * 
   * @param w is the width of the newly constructed board.
   * @param h is the height of the newly constructed board.
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
  }

  /**
   * Adds the ship to the list of myShips and return true
   * 
   * @param toAdd the ship to add
   * @return true
   */
  public boolean tryAddShip(Ship<T> toAdd) {
    myShips.add(toAdd);
    return true;
  }

  /**
   * Gives the information offered by the Ship which occupies the given
   * coordinate.
   * 
   * @param where the coordinate
   * @return the information offered by the ship which occupies the given
   *         coordinate, or return null if it is not occupied by any ships
   */
  public T whatIsAt(Coordinate where) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(where)) {
        return s.getDisplayInfoAt(where);
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
