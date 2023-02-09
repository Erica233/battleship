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
   * @return true if the coordinates are in the boundaries, otherwise false
   */
  @Override
  protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    for (Coordinate c : theShip.getCoordinates()) {
      if (0 > c.getRow() || c.getRow() >= theBoard.getHeight() || 0 > c.getColumn()
          || c.getColumn() >= theBoard.getWidth()) {
        return false;
      }
    }
    return true;
  }
}
