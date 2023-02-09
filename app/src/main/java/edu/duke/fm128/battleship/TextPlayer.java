package edu.duke.fm128.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;

public class TextPlayer {
  private final Board<Character> theBoard;
  private final BoardTextView view;
  private final BufferedReader inputReader;
  private final PrintStream out;
  private final AbstractShipFactory<Character> shipFactory;
  private final String name;

  /**
   * Constructs a TextPlayer
   *
   * @param theBoard    a board
   * @param inputSource input
   * @param out         output
   */
  public TextPlayer(String theName, Board<Character> theBoard, Reader inputSource, PrintStream out,
      V1ShipFactory v1_shipFact) {
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = new BufferedReader(inputSource);
    this.out = out;
    this.shipFactory = v1_shipFact;
    this.name = theName;
  }

  /**
   * Prints out the prompt message, creates a new placement according to the input
   *
   * @param prompt the string that will be printed out
   * @return the new constructed placement according to input
   * @throws IOException
   */
  public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    return new Placement(s);
  }

  /**
   * Reads a placement according to input, creates a basic ship
   * according to the location of placement, adds it to the board,
   * and prints out the board
   *
   * @throws IOException
   */
  public void doOnePlacement() throws IOException {
    String prompt = "Player " + name + " where do you want to place a Destroyer?";
    Placement p = readPlacement(prompt);
    Ship<Character> a_basicShip = shipFactory.makeDestroyer(p);
    theBoard.tryAddShip(a_basicShip);
    out.print(view.displayMyOwnBoard());
  }

  /**
   * A helper function to output break line
   */
  public void printLineMarker() {
    out.print("---------------------------------------------------------------------------\n");
  }

  /**
   * A helper function to output instructions
   */
  public void printInstruction() {
    printLineMarker();
    out.print("Player " + name + ": you are going to place the following ships (which are all\n" +
            "rectangular). For each ship, type the coordinate of the upper left\n" +
            "side of the ship, followed by either H (for horizontal) or V (for\n" +
            "vertical).  For example M4H would place a ship horizontally starting\n" +
            "at M4 and going to the right.  You have\n" +
            "\n" +
            "2 \"Submarines\" ships that are 1x2 \n" +
            "3 \"Destroyers\" that are 1x3\n" +
            "3 \"Battleships\" that are 1x4\n" +
            "2 \"Carriers\" that are 1x6\n");
    printLineMarker();
  }

  /**
   * display the starting (empty) board, print the instructions message, and place one ship
   *
   * @throws IOException
   */
  public void doPlacementPhase() throws IOException {
    printLineMarker();
    out.print(view.displayMyOwnBoard());
    printInstruction();
    doOnePlacement();
  }
}
