package edu.duke.fm128.battleship;

import java.util.HashSet;

/**
 * A ship that is rectangular, which extends a BasicShip
 *
 * @param <T> Character
 */
public class RectangleShip<T> extends BasicShip<T> {
  private final String name;

  /**
   * Constructs a RectangleShip
   *
   * @param name          the (type) name of the ship
   * @param upperLeft     the coordinate of the upper left part of the ship
   * @param width         the width of the ship
   * @param height        the height of the ship
   * @param myDisplayInfo the ShipDisplayInfo
   */
  public RectangleShip(String name, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> myDisplayInfo,
      ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(upperLeft, width, height), myDisplayInfo, enemyDisplayInfo);
    this.name = name;
  }

  /**
   * Constructs a RectangleShip
   *
   * @param name      the (type) name of the ship
   * @param upperLeft the coordinate of the upper left part of the ship
   * @param width     the width of the ship
   * @param height    the height of the ship
   * @param data      myData
   * @param onHit     onHit
   */
  public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(name, upperLeft, width, height, new SimpleShipDisplayInfo<>(data, onHit),
        new SimpleShipDisplayInfo<>(null, data));
  }

  /**
   * Constructs a RectangleShip
   *
   * @param upperLeft the coordinate of the upper left part of the ship
   * @param data      myData
   * @param onHit     onHit
   */
  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this("testship", upperLeft, 1, 1, data, onHit);
  }

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

  /** getters and setters **/
  public String getName() {
    return name;
  }
}
