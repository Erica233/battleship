package edu.duke.fm128.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoCollisionRuleCheckerTest {
  @Test
  void test_check_my_rule() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<>(10, 20, 'X');
    V1ShipFactory f = new V1ShipFactory();
    NoCollisionRuleChecker<Character> checker = new NoCollisionRuleChecker<>(null);
    Placement p0 = new Placement(new Coordinate(1, 2), 'H');
    Placement p1 = new Placement(new Coordinate(1, 3), 'H');
    Placement p2 = new Placement(new Coordinate(1, 4), 'H');
    Placement p3 = new Placement(new Coordinate(4, 4), 'H');
    Ship<Character> sub0 = f.makeSubmarine(p0);
    Ship<Character> sub1 = f.makeSubmarine(p1);
    Ship<Character> sub2 = f.makeSubmarine(p2);
    Ship<Character> sub3 = f.makeSubmarine(p3);
    assertNull(checker.checkMyRule(sub0, b1));
    assertNull(checker.checkMyRule(sub1, b1));
    assertNull(checker.checkMyRule(sub2, b1));
    assertNull(checker.checkMyRule(sub3, b1));
    b1.tryAddShip(sub0);
    assertNotEquals(null, checker.checkMyRule(sub0, b1));
    assertNotEquals(null, checker.checkMyRule(sub1, b1));
    assertNull(checker.checkMyRule(sub2, b1));
    assertNull(checker.checkMyRule(sub3, b1));
  }

  @Test
  void test_combine_rules() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<>(10, 20, 'X');
    V1ShipFactory f = new V1ShipFactory();
    InBoundsRuleChecker<Character> checker = new InBoundsRuleChecker<>(new NoCollisionRuleChecker<>(null));
    Placement p0 = new Placement(new Coordinate(1, 2), 'H');
    Placement p1 = new Placement(new Coordinate(-1, 2), 'H');
    Ship<Character> sub0 = f.makeSubmarine(p0);
    Ship<Character> sub1 = f.makeSubmarine(p1);

    assertNull(checker.checkPlacement(sub0, b1));
    assertNotEquals(null, checker.checkPlacement(sub1, b1));
    b1.tryAddShip(sub0);
    assertNotEquals(null, checker.checkPlacement(sub0, b1));

    NoCollisionRuleChecker<Character> checker2 = new NoCollisionRuleChecker<>(new InBoundsRuleChecker<>(null));
    assertNotEquals(null, checker2.checkPlacement(sub0, b1));
    assertNotEquals(null, checker2.checkPlacement(sub1, b1));
  }
}
