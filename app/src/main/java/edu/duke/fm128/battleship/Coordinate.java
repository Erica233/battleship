package edu.duke.fm128.battleship;

public class Coordinate {
  private final int row;
  private final int column;

  public Coordinate(int r, int c) {
    this.row = r;
    this.column = c;
  }

  public Coordinate(String descr) {
    if (descr.length() != 2) {
      throw new IllegalArgumentException("The length of Coordinate must be 2!");
    }
    char rowLetter = descr.toUpperCase().charAt(0);
    int colNum = descr.charAt(1);
    if (rowLetter < 'A' || rowLetter > 'Z') {
      throw new IllegalArgumentException("The row letter of Coordinate must be a letter!");
    }
    this.row = rowLetter - 'A';
    this.column = colNum;
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
