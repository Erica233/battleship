package edu.duke.fm128.battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * A battleship board for the battleship game
 */
public class BattleShipBoard<T> implements Board<T> {
  private final int width;
  private final int height;
  private final ArrayList<Ship<T>> myShips;
  private final PlacementRuleChecker<T> placementChecker;
  private final HashSet<Coordinate> enemyMisses;
  private final HashMap<Coordinate, T> enemyHits;
  private final T missInfo;

  /**
   * Constructs a BattleShipBoard with the specified width and height
   *
   * @param w is the width of the newly constructed board.
   * @param h is the height of the newly constructed board.
   */
  public BattleShipBoard(int w, int h, T _missInfo) {
    this(w, h, new InBoundsRuleChecker<>(null), _missInfo);
  }

  /**
   * Constructs a BattleShipBoard with the specified width and height and
   * PlacementRuleChecker
   * 
   * @param w   is the width of the newly constructed board.
   * @param h   is the height of the newly constructed board.
   * @param prc is the PlacementRuleChecker
   * @throws IllegalArgumentException if the width or height are less than or
   *                                  equal to zero.
   */
  public BattleShipBoard(int w, int h, PlacementRuleChecker<T> prc, T _missInfo) {
    if (w <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
    }
    if (h <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
    }
    this.width = w;
    this.height = h;
    this.myShips = new ArrayList<>();
    this.placementChecker = prc;
    this.enemyMisses = new HashSet<>();
    this.enemyHits = new HashMap<>();
    this.missInfo = _missInfo;
  }

  @Override
  public Ship<T> findShip(Coordinate c) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(c)) {
        return s;
      }
    }
    return null;
  }

  @Override
  public boolean allSunk() {
    for (Ship<T> s : myShips) {
      if (!s.isSunk()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public Ship<T> fireAt(Coordinate c) {
    // search for any ship that occupies coordinate c
    for (Ship<T> ship : myShips) {
      // If one is found, that Ship is "hit" by the attack and should record it
      if (ship.occupiesCoordinates(c)) {
        ship.recordHitAt(c);
        enemyHits.put(c, ship.getDisplayInfoAt(c, false));
        enemyMisses.remove(c);
        return ship;
      }
    }
    // If no ships are at this coordinate, record the miss in the enemyMisses, and
    // return null.
    enemyMisses.add(c);
    return null;
  }

  /**
   * set up a sonar scan result map
   *
   * @param shipNames available ship names
   * @return the empty scan results
   */
  public HashMap<String, Integer> setUpScanResultsMap(Set<String> shipNames) {
    HashMap<String, Integer> output = new HashMap<>();
    for (String shipName : shipNames) {
      output.put(shipName, 0);
    }
    return output;
  }

  /**
   * a helper method to generate a list of sonar scan's relative coordinates
   *
   * @param range  range of the sonar scan
   * @param center the center coordinate of sonar scan
   * @return a list of sonar scan's relative coordinates to scan
   */
  public ArrayList<Coordinate> generateSonarCoords(int range, Coordinate center) {
    ArrayList<Coordinate> output = new ArrayList<>();
    int row = center.getRow();
    int col = center.getColumn();
    for (int i = 0; i <= range; i++) {
      for (int j = range - i; j < range + i + 1; j++) {
        Coordinate c = new Coordinate(i - range + row, j - range + col);
        if (checkContain(c)) {
          output.add(new Coordinate(i - range + row, j - range + col));
        }
      }
    }
    for (int i = 1; i <= range; i++) {
      for (int j = i; j < 2 * range - i + 1; j++) {
        Coordinate c = new Coordinate(i + row, j - range + col);
        if (checkContain(c)) {
          output.add(c);
        }
      }
    }
    return output;
  }

  @Override
  public HashMap<String, Integer> scanAt(Coordinate center, Set<String> shipNames) {
    HashMap<String, Integer> scanResults = setUpScanResultsMap(shipNames);
    int range = 3;
    ArrayList<Coordinate> sonarCoords = generateSonarCoords(range, center);
    for (Ship<T> ship : myShips) {
      for (Coordinate c : sonarCoords) {
        if (ship.occupiesCoordinates(c)) {
          scanResults.put(ship.getName(), scanResults.get(ship.getName()) + 1);
        }
      }
    }
    return scanResults;
  }

  /**
   * try to add ship, also Checks the validity of the placement,
   * if it is valid, adds a ship to the list of myShips and returns true,
   * otherwise, return false
   * 
   * @param toAdd the ship to add
   * @return null if the placement is valid, otherwise return exception
   *         information
   */
  @Override
  public String tryAddShip(Ship<T> toAdd) {
    String info = placementChecker.checkPlacement(toAdd, this);
    if (info == null) {
      myShips.add(toAdd);
      return null;
    }
    return info;
  }

  @Override
  public void removeShip(Ship<T> toRemove) {
    myShips.remove(toRemove);
  }

  @Override
  public void substituteShip(Ship<Character> oldShip, Ship<Character> newShip) {
    oldShip.moveTo(newShip);
  }

  @Override
  public T whatIsAtForSelf(Coordinate where) {
    return whatIsAt(where, true);
  }

  @Override
  public T whatIsAtForEnemy(Coordinate where) {
    return whatIsAt(where, false);
  }

  /**
   * Gives the information offered by the Ship which occupies the given
   * coordinate.
   *
   * @param where  the coordinate
   * @param isSelf the boolean if it is for self
   * @return the information offered by the ship which occupies the given
   *         coordinate, or return null if it is not occupied by any ships
   */
  protected T whatIsAt(Coordinate where, boolean isSelf) {
    if (isSelf) {
      // if the specified coordinate cooresponds to a ship,
      // use its display info.
      for (Ship<T> s : myShips) {
        if (s.occupiesCoordinates(where)) {
          return s.getDisplayInfoAt(where, isSelf);
        }
      }
    }
    // However, if it does not, and we are doing this for an enemy board,
    // then check for a miss before return null.
    else {
      if (enemyHits.get(where) != null) {
        return enemyHits.get(where);
      }
      if (enemyMisses.contains(where)) {
        return missInfo;
      }
    }
    return null;
  }

  @Override
  public boolean checkContain(Coordinate c) {
    if (c.getColumn() < 0 || c.getColumn() >= width || c.getRow() < 0 || c.getRow() >= height) {
      return false;
    } else {
      return true;
    }
  }

  /** getters and setters */
  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }
}
