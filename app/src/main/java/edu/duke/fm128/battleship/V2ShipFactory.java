package edu.duke.fm128.battleship;

/**
 * A ShipFactory that used for ship creation for version 2
 */
public class V2ShipFactory implements AbstractShipFactory<Character> {

  /**
   * A helper function that creates rectangle ships
   *
   * @param where  the placement
   * @param w      width
   * @param h      height
   * @param letter the symbol letter
   * @param name   the name of the ship type
   * @return a newly constructed RectangleShip
   */
  protected Ship<Character> createRectangleShip(Placement where, int w, int h, char letter, String name) {
    int width = w;
    int height = h;
    if (where.getOrientation() != 'V' && where.getOrientation() != 'H') {
      throw new IllegalArgumentException("The orientation of the placement should be horizontal or vertical!");
    }
    if (where.getOrientation() == 'H') {
      width = h;
      height = w;
    }
    return new RectangleShip<>(name, where.getWhere(), width, height, letter, '*');
  }

  /**
   * A helper function that creates T-Shaped ships
   *
   * @param where  the placement
   * @param letter the symbol letter
   * @param name   the name of the ship type
   * @return a newly constructed T-Shaped ships
   */
  protected Ship<Character> createTShapedShip(Placement where, char letter, String name) {
    if (where.getOrientation() != 'U' && where.getOrientation() != 'R' && where.getOrientation() != 'D'
        && where.getOrientation() != 'L') {
      throw new IllegalArgumentException(
          "The orientation of the placement should be up (U), right (R), down (D), and left (L)!");
    }
    return new TShapedShip<>(name, where.getWhere(), where.getOrientation(), letter, '*');
  }

  /**
   * A helper function that creates Z-Shaped ships
   *
   * @param where  the placement
   * @param letter the symbol letter
   * @param name   the name of the ship type
   * @return a newly constructed Z-Shaped ships
   */
  protected Ship<Character> createZShapedShip(Placement where, char letter, String name) {
    if (where.getOrientation() != 'U' && where.getOrientation() != 'R' && where.getOrientation() != 'D'
        && where.getOrientation() != 'L') {
      throw new IllegalArgumentException(
          "The orientation of the placement should be up (U), right (R), down (D), and left (L)!");
    }
    return new ZShapedShip<>(name, where.getWhere(), where.getOrientation(), letter, '*');
  }

  @Override
  public Ship<Character> makeSubmarine(Placement where) {
    return createRectangleShip(where, 1, 2, 's', "Submarine");
  }

  @Override
  public Ship<Character> makeDestroyer(Placement where) {
    return createRectangleShip(where, 1, 3, 'd', "Destroyer");
  }

  /**
   * Makes Tshaped Battleships
   *
   * @param where specifies the location and orientation of the ship to make
   * @return a newly constructed Tshaped Battleships
   */
  @Override
  public Ship<Character> makeBattleship(Placement where) {
    return createTShapedShip(where, 'b', "Battleship");
  }

  /**
   * Makes Zshaped Carrier
   *
   * @param where specifies the location and orientation of the ship to make
   * @return a newly constructed Zshaped Carrier Battleships
   */
  @Override
  public Ship<Character> makeCarrier(Placement where) {
    return createZShapedShip(where, 'c', "Carrier");
  }

}
