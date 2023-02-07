package edu.duke.fm128.battleship;

public class BattleShipBoard {
  private final int width;
  private final int height;

  public BattleShipBoard(int w, int h) {
    if (w <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
    }
    if (h <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
    }
    this.width = w;
    this.height = h;
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

}
