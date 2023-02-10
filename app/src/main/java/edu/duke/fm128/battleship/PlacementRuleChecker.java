package edu.duke.fm128.battleship;

/**
 * A abstract class that is a PlacementRuleChecker
 *
 * @param <T>
 */
public abstract class PlacementRuleChecker<T> {
  private final PlacementRuleChecker<T> next;

  /**
   * Constructs a PlacementRuleChecker
   *
   * @param next the next PlacementRuleChecker
   */
  public PlacementRuleChecker(PlacementRuleChecker<T> next) {
    this.next = next;
  }

  /**
   * Checks whether they follow the placement rule.
   * Subclasses will override this method to specify how they check their own rule
   *
   * @param theShip  the ship to check
   * @param theBoard the board to check
   * @return true if they follow the placement rule, otherwise false
   */
  protected abstract String checkMyRule(Ship<T> theShip, Board<T> theBoard);

  /**
   * Checks whether they follow all the placement rules
   *
   * @param theShip  the ship to check
   * @param theBoard the board to check
   * @return true if they follow all the placement rules, otherwise false
   */
  public String checkPlacement(Ship<T> theShip, Board<T> theBoard) {
    String info = checkMyRule(theShip, theBoard);
    // if we fail our own rule: stop the placement is not legal
    if (info != null) {
      return info;
    }
    // otherwise, ask the rest of the chain.
    if (next != null) {
      return next.checkPlacement(theShip, theBoard);
    }
    // if there are no more rules, then the placement is legal
    return null;
  }

}
