package edu.duke.fm128.battleship;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * A ship that is Z-shaped, which extends a BasicShip, e.g.Carrier
 *
 * @param <T> Character
 */
public class ZShapedShip<T> extends BasicShip<T> {
  private final String name;

  /**
   * Constructs a Z-shaped Carrier
   *
   * @param name          the (type) name of the ship
   * @param upperLeft     the coordinate of the upper left part of the ship
   * @param orientation   the orientation of the ship, up (U), right (R), down
   *                      (D), and left (L))
   * @param myDisplayInfo the ShipDisplayInfo
   */
  public ZShapedShip(String name, Coordinate upperLeft, Character orientation, ShipDisplayInfo<T> myDisplayInfo,
      ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(upperLeft, orientation), myDisplayInfo, enemyDisplayInfo);
    this.name = name;
  }

  /**
   * Constructs a Z-shaped Carrier
   *
   * @param name        the (type) name of the ship
   * @param upperLeft   the coordinate of the upper left part of the ship
   * @param orientation the orientation of the ship, up (U), right (R), down (D),
   *                    and left (L))
   * @param data        myData
   * @param onHit       onHit
   */
  public ZShapedShip(String name, Coordinate upperLeft, Character orientation, T data, T onHit) {
    this(name, upperLeft, orientation, new SimpleShipDisplayInfo<>(data, onHit),
        new SimpleShipDisplayInfo<>(null, data));
  }

  /**
   * Generates the set of coordinates for a rectangle starting at upperLeft
   * whose upperleft coordinate and height are as specified
   *
   * @param upperLeft   the coordinate of the upper left part of the ship
   * @param orientation the orientation of the ship, up (U), right (R), down (D),
   *                    and left (L))
   * @return the set of coordinates occupied by the ship
   */
  public static ArrayList<Coordinate> makeCoords(Coordinate upperLeft, Character orientation) {
    ArrayList<Coordinate> coordsSet = new ArrayList<>();
    if (orientation == 'U') {
      for (int i = 0; i < 4; i++) {
        coordsSet.add(new Coordinate(upperLeft.getRow() + i, upperLeft.getColumn()));
      }
      for (int i = 0; i < 3; i++) {
        coordsSet.add(new Coordinate(upperLeft.getRow() + 2 + i, upperLeft.getColumn() + 1));
      }
    }
    if (orientation == 'R') {
      for (int i = 4; i > 0; i--) {
        coordsSet.add(new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + i));
      }
      for (int i = 2; i >= 0; i--) {
        coordsSet.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + i));
      }
    }
    if (orientation == 'D') {
      for (int i = 4; i > 0; i--) {
        coordsSet.add(new Coordinate(upperLeft.getRow() + i, upperLeft.getColumn() + 1));
      }
      for (int i = 2; i >= 0; i--) {
        coordsSet.add(new Coordinate(upperLeft.getRow() + i, upperLeft.getColumn()));
      }
    }
    if (orientation == 'L') {
      for (int i = 0; i < 4; i++) {
        coordsSet.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + i));
      }
      for (int i = 0; i < 3; i++) {
        coordsSet.add(new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 2 + i));
      }
    }
    return coordsSet;
  }

  /** getters and setters **/
  public String getName() {
    return name;
  }
}
