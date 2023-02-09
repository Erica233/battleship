package edu.duke.fm128.battleship;

/**
 * A simple ShipDisplayInfo class
 *
 * @param <T> Character
 */
public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {
  private T myData;
  private T onHit;

  /**
   * Constructs the ShipDisplayInfo given myData and onHit
   * 
   * @param d myData input
   * @param h onHit input
   */
  public SimpleShipDisplayInfo(T d, T h) {
    this.myData = d;
    this.onHit = h;
  }

  @Override
  public T getInfo(Coordinate where, boolean hit) {
    if (hit) {
      return onHit;
    } else {
      return myData;
    }
  }
}
