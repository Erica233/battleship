package edu.duke.fm128.battleship;

import java.util.Locale;

/**
 * This class stands for placement details, including the coordinate and
 * orientation
 */
public class Placement {
  private final Coordinate where;
  private final char orientation;

  /**
   * Constructs a Placement with the specified Coordinate and orientation
   * 
   * @param wh     the Coordinate of the placement
   * @param orient the orientation of the placement
   */
  public Placement(Coordinate wh, char orient) {
    this.where = wh;
    this.orientation = Character.toUpperCase(orient);
  }

  /**
   * Constructs a corresponding Placement with the specified string
   * 
   * @param descr a string including the coordinate and orientation of the
   *              placement
   * @throws IllegalArgumentException if the placement is invalid
   */
  public Placement(String descr) {
    if (descr.length() != 3) {
      throw new IllegalArgumentException("it does not have the correct format.");
    }
    this.where = new Coordinate(descr.substring(0, 2));
    char orient = descr.toUpperCase(Locale.ROOT).charAt(2);
    if ((orient != 'H' && orient != 'V') && (orient != 'U' && orient != 'R' && orient != 'D' && orient != 'L')) {
      throw new IllegalArgumentException("it does not have the correct format.");
    }
    this.orientation = descr.toUpperCase(Locale.ROOT).charAt(2);
  }

  /**
   * Gives the coordinate and orientation of the placement
   * 
   * @return a String that shows the coordinate and orientation of the placement
   */
  @Override
  public String toString() {
    return "(" + where + ", " + orientation + ")";
  }

  /**
   * Checks if two objects are the same placement, according to their coordinate
   * and orientation of the placement
   * 
   * @param o object to check
   * @return boolean that if the two objects have the same location and
   *         orientation
   */
  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Placement p = (Placement) o;
      return where.equals(p.where) && orientation == p.orientation;
    }
    return false;
  }

  /**
   * Calculates the hashcode of the placement
   * 
   * @return the hashcode number of the placement
   */
  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  /** getters and setters **/
  public Coordinate getWhere() {
    return where;
  }

  public char getOrientation() {
    return orientation;
  }
}
