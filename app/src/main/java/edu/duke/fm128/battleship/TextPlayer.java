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
  private boolean isComputer;
  private int col_currCompFireAt = -1;
  private int row_currCompFireAt = 0;

  /**
   * Constructs a TextPlayer
   *
   * @param theBoard    a board
   * @param inputSource input
   * @param out         output
   */
  public TextPlayer(String theName, Board<Character> theBoard, Reader inputSource, PrintStream out,
      AbstractShipFactory<Character> v_shipFact) {
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

  /**
   * set up available action list
   *
   */
  protected void setupAvailableActionsList() {
    availableActions.put("F", 1); // infinity, it will increase every time you finish it
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
   * read choice about want as a human player or computer player
   *
   * @param prompt string
   * @return true if want to play as a computer player
   * @throws IOException
   */
  public boolean chooseAsComputer(String prompt) throws IOException {
    out.println(prompt);
    String choice = inputReader.readLine();
    if (choice == null) {
      throw new EOFException();
    }
    choice = choice.toUpperCase(Locale.ROOT);
    if (!choice.equals("H") && !choice.equals("C")) {
      throw new IllegalArgumentException("Please enter a valid choice!");
    }
    return choice.equals("C");
  }

  /**
   * choose want to be huaman player or computer player
   *
   * @throws IOException
   */
  public void choosePlayer() throws IOException {
    String problem;
    do {
      try {
        isComputer = chooseAsComputer(
            "Player " + name + ", do you want to play as a Human (H) or as a Computer (C)?\n");
        problem = null;
      } catch (IllegalArgumentException iae) {
        problem = "choose H (Human) or C (Computer).";
      }
      if (problem != null) {
        String msg = "That choice is invalid: " + problem;
        out.println(msg);
      }
    } while (problem != null);
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
      } else {
        out.print(view.displayMyOwnBoard());
      }
    } while (problem != null);
  }

  /**
   * A helper function to output placement instructions
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
   * a helper method to generate placements for a computer player
   *
   * @return the list of placement for a computer to play
   */
  public ArrayList<Placement> generatePlacementsAsComputer() {
    ArrayList<Placement> placeListComp = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      Coordinate c = new Coordinate(i, 0);
      Placement p = new Placement(c, 'H');
      placeListComp.add(p);
    }
    for (int i = 5; i < 10; i = i + 2) {
      Coordinate c = new Coordinate(i, 0);
      Placement p = new Placement(c, 'U');
      placeListComp.add(p);
    }
    for (int i = 11; i < 14; i = i + 2) {
      Coordinate c = new Coordinate(i, 0);
      Placement p = new Placement(c, 'R');
      placeListComp.add(p);
    }
    return placeListComp;
  }

  /**
   * display the starting (empty) board, print the instructions message, and place
   * one ship
   *
   * @throws IOException
   */
  public void doPlacementPhase() throws IOException {
    if (isComputer) {
      ArrayList<Placement> placeList = generatePlacementsAsComputer();
      int i = 0;
      for (String s : shipsToPlace) {
        Ship<Character> newShip = shipCreationFns.get(s).apply(placeList.get(i));
        theBoard.tryAddShip(newShip);
        i++;
      }
    } else {
      out.print(view.displayMyOwnBoard());
      for (String s : shipsToPlace) {
        printPlacementInstruction();
        doOnePlacement(s, shipCreationFns.get(s));
      }
    }
  }

  /**
   * A helper function to generate action choices instructions
   *
   * @return a string contains possible actions
   */
  public String generateActionInstruction() {
    return "Possible actions for Player " + name + ":\n" +
        " F Fire at a square\n" +
        " M Move a ship to another square (" + availableActions.get("M") + " remaining)\n" +
        " S Sonar scan (" + availableActions.get("S") + " remaining)\n" +
        "\n" +
        "Player " + name + ", what would you like to do?\n";
  }

  /**
   * Prints out the prompt message, creates a new Coordinate according to the
   * input
   *
   * @param prompt the string that will be printed out
   * @return the new constructed Coordinate according to input
   * @throws IOException if input is empty
   */
  public String readAction(String prompt) throws IOException, IllegalArgumentException {
    out.print(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new EOFException();
    }
    s = s.toUpperCase(Locale.ROOT);
    if (availableActions.get(s) == null || availableActions.get(s) == 0) {
      throw new IllegalArgumentException("Please enter a valid action!");
    }
    return s;
  }

  /**
   * generate coordinate for a computer player to fire at at each turn
   *
   * @return a coordinate to fire at
   */
  public Coordinate generateCoordinate() {
    col_currCompFireAt++;
    Coordinate newCoord = new Coordinate(row_currCompFireAt, col_currCompFireAt);
    if (theBoard.checkContain(newCoord)) {
      return newCoord;
    }
    row_currCompFireAt++;
    col_currCompFireAt = 0;
    return new Coordinate(row_currCompFireAt, col_currCompFireAt);
  }

  /**
   * play one turn as a computer player. It will only prints out the action
   * results
   *
   * @param enemyBoard the enemy board
   * @throws IllegalArgumentException
   */
  public void playOneTurnAsComputer(Board<Character> enemyBoard)
      throws IllegalArgumentException {
    Coordinate c = generateCoordinate();
    Ship<Character> s = enemyBoard.fireAt(c);
    if (s == null) {
      out.print(name + " missed!\n");
    } else {
      out.print(name + " hit a " + s.getName() + " at " + c + "!\n");
    }
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
    if (isComputer) {
      playOneTurnAsComputer(enemyBoard);
      return;
    }
    out.print("Player " + name + "'s turn:\n");
    out.print(view.displayMyBoardWithEnemyNextToIt(enemyView, "Your ocean", "Player " + enemyName + "'s ocean"));
    String problem;
    do {
      try {
        // choose action
        String actionInstruction = generateActionInstruction();
        String action = readAction(actionInstruction);
        if (action.equals("F")) {
          tryFireAction(enemyBoard);
        }
        if (action.equals("M")) {
          tryMoveAction();
        }
        if (action.equals("S")) {
          trySonarScanAction(enemyBoard);
        }
        problem = null;
        if (!action.equals("F")) {
          availableActions.put(action, availableActions.get(action) - 1);
        }
      } catch (IllegalArgumentException iae) {
        problem = "it does not have the correct format!" + iae;
        out.println("Invalid action!");
      }
      if (problem != null) {
        String msg = "That action is invalid: " + problem;
        out.println(msg);
      }
    } while (problem != null);
  }

  /**
   * Prints out the prompt message, creates a new Coordinate according to the
   * input,
   *
   * @param prompt the string that will be printed out
   * @return the new constructed Coordinate according to input
   * @throws IOException              if input is empty
   * @throws IllegalArgumentException if the coordinate is not within board
   */
  public Coordinate readCoordinate(String prompt) throws IOException, IllegalArgumentException {
    out.print(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new EOFException("The coordinate is empty!");
    }
    Coordinate c = new Coordinate(s);
    if (!theBoard.checkContain(c)) {
      throw new IllegalArgumentException("The Coordinate is out of board!");
    }
    return c;
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
   * @throws IOException              if no input for coordinate
   * @throws IllegalArgumentException if the input coordinate is invalid
   */
  public void tryFireAction(Board<Character> enemyBoard)
      throws IOException, IllegalArgumentException {
    String prompt = "Player " + name + ", please enter a coordinate where you want to fire at?\n";
    Coordinate c = readCoordinate(prompt);
    Ship<Character> s = enemyBoard.fireAt(c);
    // problem = null;
    if (s == null) {
      out.print("You missed!\n");
    } else {
      out.print("You hit a " + s.getName() + "!\n");
    }
  }

  /**
   * Lets the player play for one turn.
   * It displays two boards side by side to the player first,
   * one for the player's own board, the other for the enemy.
   * Then prompts the player to fire at a coordinate. If the
   * coordinates are invalid, it prompts the player to enter a valid choice.
   * It then reports the result.
   *
   * @throws IOException              if no input for coordinate
   * @throws IllegalArgumentException if the input coordinate is invalid
   */
  public void tryMoveAction()
      throws IOException, IllegalArgumentException {
    // read coordinate
    String prompt = "Player " + name + ", which ship do you want to move? Please enter a coordinate!\n";
    Coordinate c = readCoordinate(prompt);
    Ship<Character> theShipToMove = theBoard.findShip(c);
    if (theShipToMove == null) {
      throw new IllegalArgumentException("The coordinate is not belong to your ships!");
    }

    // read placement
    Placement p = readPlacement(
        "Player " + name + " where do you want to place the " + theShipToMove.getName() + " you selected?");
    Ship<Character> newShip = shipCreationFns.get(theShipToMove.getName()).apply(p);
    theBoard.removeShip(theShipToMove);
    theBoard.tryAddShip(newShip);
    theBoard.substituteShip(theShipToMove, newShip);
  }

  /**
   * try to do sonar scan action, and print out results about
   * how many of each type of ship within that sonar scan area
   *
   * @param enemyBoard the enemy board to scan
   * @throws IOException
   * @throws IllegalArgumentException
   */
  public void trySonarScanAction(Board<Character> enemyBoard)
      throws IOException, IllegalArgumentException {
    String prompt = "Player " + name + ", where do you want to do sonar scan? Please enter a center coordinate.\n";
    Coordinate center = readCoordinate(prompt);
    // scan
    HashMap<String, Integer> scanResults = enemyBoard.scanAt(center, shipCreationFns.keySet());

    // print results
    for (String shipName : scanResults.keySet()) {
      out.print(shipName + " occupy " + scanResults.get(shipName) + " squares\n");
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
