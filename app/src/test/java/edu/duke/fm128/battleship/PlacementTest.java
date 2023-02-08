package edu.duke.fm128.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlacementTest {
    @Test
    public void get_where_and_orientation() {
        Coordinate c1 = new Coordinate(1, 2);
        Placement p1 = new Placement(c1, 'v');
        Placement p2 = new Placement(c1, 'V');
        assertEquals(new Coordinate(1, 2), p1.getWhere());
        assertEquals('V', p2.getOrientation());
    }

    @Test
    void test_string_constructor_valid_cases() {
        Placement p1 = new Placement("B3h");
        assertEquals(new Coordinate("B3"), p1.getWhere());
        assertEquals('H', p1.getOrientation());
        Placement p2 = new Placement("Z0V");
        assertEquals(new Coordinate("Z0"), p2.getWhere());
        assertEquals('V', p2.getOrientation());
        Placement p3 = new Placement("A9v");
        assertEquals(new Coordinate("A9"), p3.getWhere());
        assertEquals('V', p3.getOrientation());
        Placement p4 = new Placement("D5H");
        assertEquals(new Coordinate("D5"), p4.getWhere());
        assertEquals('H', p4.getOrientation());
    }
    @Test
    public void test_string_constructor_error_cases() {
        assertThrows(IllegalArgumentException.class, () -> new Placement("99"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("A9HH"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("A-H"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("-9H"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("H9g"));
    }

    @Test
    public void test_toString() {
        Placement p1 = new Placement("D5H");
        assertEquals("((3, 5), H)", p1.toString());
    }

    @Test
    public void test_equals() {
        Coordinate c1 = new Coordinate(1, 2);
        Placement p1 = new Placement(c1, 'v');
        Placement p2 = new Placement(c1, 'V');
        Placement p3 = new Placement("B2h");
        Placement p4 = new Placement("A2V");
        assertEquals(p1, p1);
        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertNotEquals(p4, p3);
        assertNotEquals("((1, 2), V)", p1);
        assertNotEquals(p1, "((1, 2), V)");
        assertTrue(!p1.equals(p4));
    }

    @Test
    public void test_hashCode() {
        Placement p1 = new Placement("B2h");
        Placement p2 = new Placement("B2h");
        Placement p3 = new Placement("A2V");
        Placement p4 = new Placement("D5H");
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1.hashCode(), p3.hashCode());
        assertNotEquals(p1.hashCode(), p4.hashCode());
    }
}
