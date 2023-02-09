package edu.duke.fm128.battleship;

import java.util.HashMap;

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
   * @param where the coordinates where it is at
   * @param _myDisplayInfo my ShipDisplayInfo
   */
  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> _myDisplayInfo) {
    this.myPieces = new HashMap<>();
    for (Coordinate c : where) {
      this.myPieces.put(c, false);
    }
    this.myDisplayInfo = _myDisplayInfo;
  }

  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    // TODO Auto-generated method stub
    return myPieces.get(where) != null;
  }

  @Override
  public boolean isSunk() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void recordHitAt(Coordinate where) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean wasHitAt(Coordinate where) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public T getDisplayInfoAt(Coordinate where) {
    //TODO this is not right.  We need to
    //look up the hit status of this coordinate
    return myDisplayInfo.getInfo(where, false);
  }
}
