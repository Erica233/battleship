package edu.duke.fm128.battleship;

public class V1ShipFactory implements AbstractShipFactory<Character> {

  protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name) {
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

  @Override
  public Ship<Character> makeSubmarine(Placement where) {
    return createShip(where, 1, 2, 's', "Submarine");
  }

  @Override
  public Ship<Character> makeDestroyer(Placement where) {
    return createShip(where, 1, 3, 'd', "Destroyer");
  }

  @Override
  public Ship<Character> makeBattleship(Placement where) {
    return createShip(where, 1, 4, 'b', "Battleship");
  }

  @Override
  public Ship<Character> makeCarrier(Placement where) {
    return createShip(where, 1, 6, 'c', "Carrier");
  }

}
