package edu.duke.fm128.battleship;

public class Coordinate {
  private final int row;
  private final int column;

  public Coordinate(int r, int c) {
    this.row = r;
    this.column = c;
  }

  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Coordinate c = (Coordinate) o;
      return row == c.row && column == c.column;
    }
    return false;
  }

  @Override
  public String toString() {
    return "(" + row + ", " + column + ")";
  }
  
  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }
}
