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
}
