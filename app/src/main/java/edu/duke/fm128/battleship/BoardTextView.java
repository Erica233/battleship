package edu.duke.fm128.battleship;

import java.util.function.Function;

/**
 * This class handles textual display of
 * a Board (i.e., converting it to a string to show
 * to the user).
 * It supports two ways to display the Board:
 * one for the player's own board, and one for the
 * enemy's board.
 */
public class BoardTextView {
  /**
   * The Board to display
   */
  private final Board<Character> toDisplay;

  /**
   * Constructs a BoardView, given the board it will display.
   * 
   * @param toDisplay is the Board to display
   * @throws IllegalArgumentException if the board is larger than 10x26.
   */
  public BoardTextView(Board<Character> toDisplay) {
    this.toDisplay = toDisplay;
    if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
      throw new IllegalArgumentException(
          "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }
  }

  /**
   * Displays the information of my board
   *
   * @return the String that displays the information of my board
   */
  public String displayMyOwnBoard() {
    return displayAnyBoard((c) -> toDisplay.whatIsAtForSelf(c));
  }

  /**
   * Displays the information of the enemy's board
   *
   * @return the String that displays the information of the enemy's board
   */
  public String displayEnemyBoard() {
    return displayAnyBoard((c) -> toDisplay.whatIsAtForEnemy(c));
  }

  /**
   * Displays both boards side by side,
   * where "this" view's board's "my own board" on the left,
   * and enemyView's "enemy board" on the right
   *
   * @param enemyView textual display of enemy's board
   * @param myHeader the header above my board
   * @param enemyHeader the header above enemy's board
   * @return a String that is the textual display of both boards
   */
  public String displayMyBoardWithEnemyNextToIt(BoardTextView enemyView, String myHeader, String enemyHeader) {
    String myBoardMyView = displayMyOwnBoard();
    String enemyBoardEnemyView = enemyView.displayEnemyBoard();
    String [] myViewLines = myBoardMyView.split("\n");
    String [] enemyViewLines = enemyBoardEnemyView.split("\n");
    StringBuilder output = new StringBuilder();
    output.append(" ".repeat(5)).append(myHeader).append(" ".repeat(2 * toDisplay.getWidth() + 22 - 5 - myHeader.length())).append(enemyHeader).append("\n");
    for (int i = 0; i < myViewLines.length; i++) {
      output.append(myViewLines[i]).append(" ".repeat(2 * toDisplay.getWidth() + 19 - myViewLines[i].length())).append(enemyViewLines[i]).append("\n");
    }
    return output.toString();
  }

  /**
   * Displays the information of the board
   *
   * @param getSquareFn the function to apply
   * @return the String that displays the information of the board
   */
  protected String displayAnyBoard(Function<Coordinate, Character> getSquareFn) {
    StringBuilder ans = new StringBuilder();
    String sep;
    Character info;
    String header = makeHeader();
    ans.append(header);
    for (int r = 0; r < toDisplay.getHeight(); r++) {
      sep = " ";
      char sym = (char) ((int) 'A' + r);
      ans.append(sym);
      for (int c = 0; c < toDisplay.getWidth(); c++) {
        ans.append(sep);
        info = getSquareFn.apply(new Coordinate(r, c));
        if (info == null) {
          info = ' ';
        }
        ans.append(info);
        sep = "|";
      }
      sep = " ";
      ans.append(sep);
      ans.append(sym);
      ans.append("\n");
    }
    ans.append(header);
    return ans.toString();
  }

  /**
   * This makes the header line, e.g. 0|1|2|3|4\n
   * 
   * @return the String that is the header line for the given board
   */
  String makeHeader() {
    StringBuilder ans = new StringBuilder("  "); // README shows two spaces at
    String sep = ""; // start with nothing to separate, then switch to | to separate
    for (int i = 0; i < toDisplay.getWidth(); i++) {
      ans.append(sep);
      ans.append(i);
      sep = "|";
    }
    ans.append("\n");
    return ans.toString();
  }
}
