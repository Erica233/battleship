package edu.duke.fm128.battleship;

import java.util.HashSet;

public class RectangleShip {
  /**
   * Generates the set of coordinates for a rectangle starting at upperLeft
   * whose width and height are as specified
   *
   * @param upperLeft the coordinate of the upper left part of the ship
   * @param width     the width of the ship
   * @param height    the height of the ship
   * @return the set of coordinates occupied by the ship
   */
  public static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
    HashSet<Coordinate> coordsSet = new HashSet<>();
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        coordsSet.add(new Coordinate(upperLeft.getRow() + r, upperLeft.getColumn() + c));
      }
    }
    return coordsSet;
  }

  /*
   * public RectangleShip(Coordinate upperLeft, int width, int height) {
   * //super(makeCoords(upperLeft, width, height)); //specify how to call parent
   * class constructor
   * }
   */

}
