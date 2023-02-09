/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.fm128.battleship;

import java.io.*;

public class App {
  private final TextPlayer player1;
  private final TextPlayer player2;

  /**
   * Constructs an App
   *
   * @param p1 player 1
   * @param p2 player 2
   */
  public App(TextPlayer p1, TextPlayer p2) {
    this.player1 = p1;
    this.player2 = p2;
  }

  public void doPlacementPhase() throws IOException {
    player1.doOnePlacement();
  }

  public static void main(String[] args) throws IOException {
    Board<Character> b1 = new BattleShipBoard<>(10, 20);
    Board<Character> b2 = new BattleShipBoard<>(10, 20);
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    V1ShipFactory factory = new V1ShipFactory();

    TextPlayer p1 = new TextPlayer("A", b1, input, System.out, factory);
    TextPlayer p2 = new TextPlayer("B", b2, input, System.out, factory);

    App app = new App(p1, p2);
    app.doPlacementPhase();
  }
}
