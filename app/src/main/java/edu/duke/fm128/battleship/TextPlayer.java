package edu.duke.fm128.battleship;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.function.Function;

/**
 * A TextPlayer
 *
 */
public class TextPlayer {
  private final Board<Character> theBoard;
  private final BoardTextView view;
  private final BufferedReader inputReader;
  private final PrintStream out;
  private final AbstractShipFactory<Character> shipFactory;
  private final String name;
  final ArrayList<String> shipsToPlace;
  final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
  final HashMap<String, Integer> availableActions;

  /**
   * Constructs a TextPlayer
   *
   * @param theBoard    a board
   * @param inputSource input
   * @param out         output
   */
  public TextPlayer(String theName, Board<Character> theBoard, Reader inputSource, PrintStream out,
      AbstractShipFactory v_shipFact) {
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = (BufferedReader) inputSource;
    this.out = out;
    this.shipFactory = v_shipFact;
    this.name = theName;
    this.shipsToPlace = new ArrayList<>();
    this.shipCreationFns = new HashMap<>();
    this.availableActions = new HashMap<>();
    setupShipCreationMap();
    setupShipCreationList();
    setupAvailableActionsList();
  }

  protected void setupAvailableActionsList() {
    availableActions.put("M", 3);
    availableActions.put("S", 3);
  }

  /**
   * set up ships creation function
   */
  protected void setupShipCreationMap() {
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
  }

  /**
   * set up ship creation list
   */
  protected void setupShipCreationList() {
    shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
    shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
    shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
    shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
  }

  /**
   * Prints out the prompt message, creates a new placement according to the input
   *
   * @param prompt the string that will be printed out
   * @return the new constructed placement according to input
   * @throws IOException if input is empty
   */
  public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new EOFException();
    }
    return new Placement(s);
  }

  /**
   * Reads a placement according to input, creates a basic ship
   * according to the location of placement, adds it to the board,
   * and prints out the board
   *
   * @throws IOException
   */
  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn)
      throws IOException, IllegalArgumentException {
    String problem;
    do {
      try {
        Placement p = readPlacement("Player " + name + " where do you want to place a " + shipName + "?");
        Ship<Character> s = createFn.apply(p);
        problem = theBoard.tryAddShip(s);
      } catch (IllegalArgumentException iae) {
        problem = "it does not have the correct format";
      }
      if (problem != null) {
        String msg = "That placement is invalid: " + problem;
        out.println(msg);
      }
      out.print(view.displayMyOwnBoard());
    } while (problem != null);
  }

  /**
   * A helper function to output instructions
   */
  public void printPlacementInstruction() {
    out.println("Player " + name + ": you are going to place the following ships (which are all\n" +
        "rectangular). For each ship, type the coordinate of the upper left\n" +
        "side of the ship, followed by either H (for horizontal) or V (for\n" +
        "vertical).  For example M4H would place a ship horizontally starting\n" +
        "at M4 and going to the right.  You have\n" +
        "\n" +
        "2 \"Submarines\" ships that are 1x2 \n" +
        "3 \"Destroyers\" that are 1x3\n" +
        "3 \"Battleships\" that are 1x4\n" +
        "2 \"Carriers\" that are 1x6\n");
  }

  /**
   * display the starting (empty) board, print the instructions message, and place
   * one ship
   *
   * @throws IOException
   */
  public void doPlacementPhase() throws IOException {
    out.print(view.displayMyOwnBoard());
    for (String s : shipsToPlace) {
      printPlacementInstruction();
      doOnePlacement(s, shipCreationFns.get(s));
    }
  }

  /**
   * Prints out the prompt message, creates a new Coordinate according to the
   * input
   *
   * @param prompt the string that will be printed out
   * @return the new constructed Coordinate according to input
   * @throws IOException if input is empty
   */
  public Coordinate readCoordinate(String prompt) throws IOException {
    while (true) {
      try {
        out.print(prompt);
        String s = inputReader.readLine();
        if (s == null) {
          throw new EOFException();
        }
        return new Coordinate(s);
      } catch (IllegalArgumentException iae) {
        out.print("Please enter a valid attack!\n");
      }
    }
  }

  /**
   * Prints out the prompt message, creates a new Coordinate according to the
   * input
   *
   * @return the valid action according to input
   * @throws IOException if input is empty
   */
  public String chooseAction(String prompt) throws IOException, IllegalArgumentException {
    out.print(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new EOFException();
    }
    s = s.toUpperCase(Locale.ROOT);
    if (!s.equals("F") && !s.equals("M") && !s.equals("S")) {
      throw new IllegalArgumentException("Please enter valid action!");
    }
    if (availableActions.get(s) == 0) {
      throw new IllegalArgumentException("Please enter available action!");
    }
    return s;
  }

  public String generateActionPrompt() {
    String actionPrompt = "Possible actions for Player " + name + ":\n" +
            " F Fire at a square\n" +
            " M Move a ship to another square (" + availableActions.get("M") + " remaining)\n" +
            " S Sonar scan (" + availableActions.get("M") + " remaining)\n" +
            "\n" +
            "Player " + name + ", what would you like to do?\n";
    return actionPrompt;
  }

  public void chooseFireAction(Board<Character> enemyBoard) throws IOException {
    String firePrompt = "Player " + name + ", please enter a coordinate where you want to fire at?\n";
    Coordinate c = readCoordinate(firePrompt);
    Ship<Character> s = enemyBoard.fireAt(c);
    if (s == null) {
      out.print("You missed!\n");
    } else {
      out.print("You hit a " + s.getName() + "!\n");
    }
  }

  public void chooseMoveAction() throws IOException, IllegalArgumentException {
    String coordinatePrompt = "Player " + name + ", which ship do you want to move? Please enter a coordinate!\n";
    //type coordinate
    out.print(coordinatePrompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new EOFException();
    }
    Coordinate c = new Coordinate(s);
    Ship<Character> theShipToMove = theBoard.findShip(c);
    if (theShipToMove == null) {
      throw new IllegalArgumentException("The coordinate is not belong to your ships!");
    }

    //type placement
    String s2 = inputReader.readLine();
    if (s2 == null) {
      throw new EOFException();
    }
    Placement p = readPlacement("Player " + name + " where do you want to place the " + theShipToMove.getName() + " you selected?");
    moveShip(theShipToMove, p);
  }

  public void chooseSonarScanAction() {
    String prompt = "";

  }

  /**
   * Lets the player play for one turn.
   * It displays two boards side by side to the player first,
   * one for the player's own board, the other for the enemy.
   * Then prompts the player to fire at a coordinate. If the
   * coordinates are invalid, it prompts the player to enter a valid choice.
   * It then reports the result.
   *
   * @param enemyBoard the enemy's board
   * @param enemyView  the enemy's BoardTextView
   * @param enemyName  the enemy's name
   * @throws IOException              if no input for coordinate
   * @throws IllegalArgumentException if the input coordinate is invalid
   */
  public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String enemyName)
      throws IOException, IllegalArgumentException {
    out.print("Player " + name + "'s turn:\n");
    out.print(view.displayMyBoardWithEnemyNextToIt(enemyView, "Your ocean", "Player " + enemyName + "'s ocean"));

    while (true) {
      try {
        //choose action
        String actionPrompt = generateActionPrompt();
        String action = chooseAction(actionPrompt);

        //if choose fire
        if (action.equals("F")) {
          chooseFireAction(enemyBoard);
        }
        //if choose move
        if (action.equals("M")) {
          chooseMoveAction();
        }
        //if choose sonar scan
        if (action.equals("S")) {
          chooseSonarScanAction();
        }

        return s;
      } catch (IllegalArgumentException iae) {
        out.print("Please enter a valid action!\n");
      }
    }
  }


  /**
   * check if it's lose
   *
   * @return true if lose
   */
  public boolean isLose() {
    return theBoard.allSunk();
  }

  /** getters and setters **/
  public String getName() {
    return name;
  }

  public Board<Character> getTheBoard() {
    return theBoard;
  }

  public BoardTextView getView() {
    return view;
  }
}
