package edu.duke.fm128.battleship;

import java.nio.charset.CoderResult;

/**
 * A class that is a NoCollisionRuleChecker
 *
 * @param <T>
 */
public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {
    /**
     * Constructs a NoCollisionRuleChecker
     *
     * @param next the next NoCollisionRuleChecker
     */
    public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
        super(next);
    }

    /**
     * checks the rule that theShip does not collide with anything else on the Board
     * (that all the squares it needs are empty)
     *
     * @param theShip  the ship to check
     * @param theBoard the board to check
     * @return true if theShip does not collide with anything else on the Board, otherwise false
     */
    @Override
    protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
        for (Coordinate c: theShip.getCoordinates()) {
            if (theBoard.whatIsAt(c) != null) {
                return false;
            }
        }
        return true;
    }
}
