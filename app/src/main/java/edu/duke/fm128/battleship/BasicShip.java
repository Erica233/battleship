package edu.duke.fm128.battleship;

import java.util.HashMap;

public class BasicShip implements Ship<Character> {
  protected HashMap<Coordinate, Boolean> myPieces;

  public BasicShip(Coordinate c) {
    myPieces = new HashMap<>();
    myPieces.put(c, false);
  }

  public BasicShip(Iterable<Coordinate> where) {
    this.myPieces = new HashMap<>();
    for (Coordinate c : where) {
      this.myPieces.put(c, false);
    }
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
  public Character getDisplayInfoAt(Coordinate where) {
    // TODO Auto-generated method stub
    return 's';
  }
}
