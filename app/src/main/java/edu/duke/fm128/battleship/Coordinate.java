package edu.duke.fm128.battleship;

import java.util.Locale;

/**
 * This class stands for a coordinate with row and column number
 */
public class Coordinate {
  private final int row;
  private final int column;

  /**
   * Constructs a Coordinate with the specified row and column number
   * @param r is the row number of the newly constructed coordinate.
   * @param c is the column number of the newly constructed coordinate.
   */
  public Coordinate(int r, int c) {
    this.row = r;
    this.column = c;
  }

  /**
   * Constructs a corresponding Coordinate with the specified string
   * @param descr a string including row letter and column number
   * @throws IllegalArgumentException if the string length is not equal to 2,
   * or the row letter is not between 'A' and 'Z' (ignoring cases),
   * or the column number is less than 0 or larger than 9.
   */
  public Coordinate(String descr) {
    if (descr.length() != 2) {
      throw new IllegalArgumentException("The length of Coordinate must be 2!");
    }
    descr = descr.toUpperCase(Locale.ROOT);
    int colNum = descr.charAt(1);
    char rowLetter = descr.charAt(0);
    if (rowLetter < 'A' || rowLetter > 'Z') {
      throw new IllegalArgumentException("The row letter of Coordinate must be a letter!");
    }
    if (colNum < '0' || colNum > '9') {
      throw new IllegalArgumentException("The row letter of Coordinate must be a number between 0 and 9!");
    }
    this.row = rowLetter - 'A';
    this.column = colNum - '0';
  }

  /**
   * Checks if two objects are the same coordinates, according to their row and column numbers
   * @param o object to check
   * @return boolean that if the two objects are the same
   */
  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Coordinate c = (Coordinate) o;
      return row == c.row && column == c.column;
    }
    return false;
  }

  /**
   * Gives the row and column number of the coordinate
   * @return a String that shows the row and column number of the coordinate
   */
  @Override
  public String toString() {
    return "(" + row + ", " + column + ")";
  }

  /**
   * Calculates the hashcode of the coordinate
   * @return the hashcode number of the coordinate
   */
  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  /** getters and setters **/
  public int getRow() {
    return row;
  }
  public int getColumn() {
    return column;
  }
}
