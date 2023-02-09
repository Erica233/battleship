package edu.duke.fm128.battleship;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class TextPlayerTest {
    private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        Board<Character> board = new BattleShipBoard<>(w, h);
        V1ShipFactory shipFactory = new V1ShipFactory();
        return new TextPlayer("A", board, input, output, shipFactory);
    }

    @Test
    void test_read_placement() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(10, 20, "B2V\nC8H\na4v\n", bytes);

        String prompt = "Please enter a location for a ship:";
        Placement[] expected = new Placement[3];
        expected[0] = new Placement(new Coordinate(1, 2), 'V');
        expected[1] = new Placement(new Coordinate(2, 8), 'H');
        expected[2] = new Placement(new Coordinate(0, 4), 'V');

        for (Placement placement : expected) {
            Placement p = player.readPlacement(prompt);
            assertEquals(p, placement); // did we get the right Placement back
            assertEquals(prompt + "\n", bytes.toString()); // should have printed prompt and newline
            bytes.reset(); // clear out bytes for next time around
        }
    }

    @Test
    void test_do_one_placement() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(4, 3, "A2v\n", bytes);

        String prompt = "Player A where do you want to place a Destroyer?";

        player.doOnePlacement();

        String expected = "  0|1|2|3\n" +
                "A  | |d|  A\n" +
                "B  | |d|  B\n" +
                "C  | |d|  C\n" +
                "  0|1|2|3\n";
        assertEquals(prompt + "\n" + expected, bytes.toString());
    }
}