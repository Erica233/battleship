package edu.duke.fm128.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InBoundsRuleCheckerTest {

  @Test
  void test_check_my_rule_and_placement() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<>(10, 20, 'X');
    V1ShipFactory f = new V1ShipFactory();
    InBoundsRuleChecker<Character> checker = new InBoundsRuleChecker<>(null);
    Placement p0 = new Placement(new Coordinate(1, 2), 'V');
    Placement p1 = new Placement(new Coordinate(-1, 2), 'V');
    Placement p2 = new Placement(new Coordinate(1, -2), 'H');
    Placement p3 = new Placement(new Coordinate(100, 2), 'V');
    Placement p4 = new Placement(new Coordinate(1, 200), 'V');
    Ship<Character> sub0 = f.makeSubmarine(p0);
    Ship<Character> sub1 = f.makeSubmarine(p1);
    Ship<Character> sub2 = f.makeSubmarine(p2);
    Ship<Character> sub3 = f.makeSubmarine(p3);
    Ship<Character> sub4 = f.makeSubmarine(p4);
    assertNull(checker.checkPlacement(sub0, b1));
    assertNotEquals(null, checker.checkPlacement(sub1, b1));
    assertNotEquals(null, checker.checkPlacement(sub2, b1));
    assertNotEquals(null, checker.checkPlacement(sub3, b1));
    assertNotEquals(null, checker.checkPlacement(sub4, b1));
    assertNull(checker.checkMyRule(sub0, b1));
    assertNotEquals(null, checker.checkMyRule(sub1, b1));
    assertNotEquals(null, checker.checkMyRule(sub2, b1));
    assertNotEquals(null, checker.checkMyRule(sub3, b1));
    assertNotEquals(null, checker.checkMyRule(sub4, b1));
  }
}
