package edu.duke.fm128.battleship;

/**
 * A class that is a InBoundsRuleChecker
 *
 * @param <T>
 */
public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {

  /**
   * Constructs a InBoundsRuleChecker
   *
   * @param next the next InBoundsRuleChecker
   */
  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  /**
   * iterate over all the coordinates in theShip and check that they are
   * in bounds on theBoard (i.e. 0 <= row < height and 0 <= column < width)
   *
   * @param theShip  the ship to check
   * @param theBoard the board to check
   * @return null if the coordinates are in the boundaries, otherwise return the
   *         detail message
   */
  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    for (Coordinate c : theShip.getCoordinates()) {
      if (0 > c.getRow()) {
        return "the ship goes off the top of the board.";
      }
      if (c.getRow() >= theBoard.getHeight()) {
        return "the ship goes off the bottom of the board.";
      }
      if (0 > c.getColumn()) {
        return "the ship goes off the left of the board.";
      }
      if (c.getColumn() >= theBoard.getWidth()) {
        return "the ship goes off the right of the board.";
      }
    }
    return null;
  }
}
