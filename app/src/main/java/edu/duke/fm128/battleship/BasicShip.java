package edu.duke.fm128.battleship;

import java.util.HashMap;
import java.util.Set;

/**
 * Abstract class for a basic ship
 *
 * @param <T> Character
 */
public abstract class BasicShip<T> implements Ship<T> {
  /**
   * tracks all the coordinates it occupies, and track which ones have been hit
   */
  protected HashMap<Coordinate, Boolean> myPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;

  /**
   * Constructs a BasicShip
   *
   * @param where          the coordinates where it is at
   * @param _myDisplayInfo my ShipDisplayInfo
   */
  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> _myDisplayInfo) {
    this.myPieces = new HashMap<>();
    for (Coordinate c : where) {
      this.myPieces.put(c, false);
    }
    this.myDisplayInfo = _myDisplayInfo;
  }

  public Set<Coordinate> getCoordinates() {
    return myPieces.keySet();
  }

  /**
   * a protected helper method to check whether the coordinate is part of the ship
   *
   * @param c a coordinate that needs to check
   */
  protected void checkCoordinateInThisShip(Coordinate c) {
    if (!myPieces.containsKey(c)) {
      throw new IllegalArgumentException("This coordinate " + c.toString() + " is not part of the Ship!");
    }
  }

  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    // return myPieces.get(where) != null;
    return myPieces.containsKey(where);
  }

  @Override
  public boolean isSunk() {
    for (Boolean val : myPieces.values()) {
      if (!val) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void recordHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    myPieces.replace(where, true);
  }

  @Override
  public boolean wasHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    return myPieces.get(where);
  }

  @Override
  public T getDisplayInfoAt(Coordinate where) {
    return myDisplayInfo.getInfo(where, wasHitAt(where));
  }
}
