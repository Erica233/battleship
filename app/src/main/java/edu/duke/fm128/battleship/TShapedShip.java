package edu.duke.fm128.battleship;

import org.checkerframework.checker.units.qual.C;

import java.util.HashSet;

/**
 * A ship that is T-shaped, which extends a BasicShip, e.g.BattleShip
 *
 * @param <T> Character
 */
public class TShapedShip<T> extends BasicShip<T>  {
    private final String name;

    /**
     * Constructs a T-shaped BattleShip
     *
     * @param name          the (type) name of the ship
     * @param upperLeft     the coordinate of the upper left part of the ship
     * @param orientation   the orientation of the ship, up (U), right (R), down (D), and left (L))
     * @param myDisplayInfo the ShipDisplayInfo
     */
    public TShapedShip(String name, Coordinate upperLeft, Character orientation, ShipDisplayInfo<T> myDisplayInfo,
                         ShipDisplayInfo<T> enemyDisplayInfo) {
        super(makeCoords(upperLeft, orientation), myDisplayInfo, enemyDisplayInfo);
        this.name = name;
    }

    /**
     * Constructs a T-shaped BattleShip
     *
     * @param name      the (type) name of the ship
     * @param upperLeft the coordinate of the upper left part of the ship
     * @param orientation   the orientation of the ship, up (U), right (R), down (D), and left (L))
     * @param data      myData
     * @param onHit     onHit
     */
    public TShapedShip(String name, Coordinate upperLeft, Character orientation, T data, T onHit) {
        this(name, upperLeft, orientation, new SimpleShipDisplayInfo<>(data, onHit),
                new SimpleShipDisplayInfo<>(null, data));
    }

    /**
     * Generates the set of coordinates for a rectangle starting at upperLeft
     * whose width and height are as specified
     *
     * @param upperLeft     the coordinate of the upper left part of the ship
     * @param orientation   the orientation of the ship, up (U), right (R), down (D), and left (L))
     * @return the set of coordinates occupied by the ship
     */
    public static HashSet<Coordinate> makeCoords(Coordinate upperLeft, Character orientation) {
        HashSet<Coordinate> coordsSet = new HashSet<>();
        if (orientation == 'U') {
            coordsSet.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn()));
            coordsSet.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 1));
            coordsSet.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 2));
            coordsSet.add(new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 1));
        }
        if (orientation == 'R') {
            coordsSet.add(new Coordinate(upperLeft.getRow(), upperLeft.getColumn()));
            coordsSet.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn()));
            coordsSet.add(new Coordinate(upperLeft.getRow() + 2, upperLeft.getColumn()));
            coordsSet.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 1));
        }
        if (orientation == 'D') {
            coordsSet.add(new Coordinate(upperLeft.getRow(), upperLeft.getColumn()));
            coordsSet.add(new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 1));
            coordsSet.add(new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 2));
            coordsSet.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 1));
        }
        if (orientation == 'L') {
            coordsSet.add(new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 1));
            coordsSet.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 1));
            coordsSet.add(new Coordinate(upperLeft.getRow() + 2, upperLeft.getColumn() + 1));
            coordsSet.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn()));
        }
        return coordsSet;
    }

    /** getters and setters **/
    public String getName() {
        return name;
    }
}
