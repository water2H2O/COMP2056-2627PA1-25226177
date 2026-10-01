import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * This file is used for testing your code. You can ignore the entire file during your development
 * You can click the button next to TestClass to test your code.
 *
 * This is also how we are going to grade your work! Of course, there will be more test cases when
 * we grade your assignment.
 *
 * Some methods is not tested in this file, but we will test them when we grade your assignment.
 *
 * To use this file, open this in IntelliJ and click the red light bulb on Line 24.
 * Select "Add JUnit5 to classpath" and click OK. Then you can run the tests by clicking the green triangle next to the class name.
 *
 *
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestClass {
    private ByteArrayOutputStream out;
    private Connect4 game;

    @BeforeEach
    public void setup() {
        out = new ByteArrayOutputStream();
        PrintStream console = System.out;
        PrintStream dualStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                console.write(b);
                out.write(b);
            }
        });
        System.setOut(dualStream);
        game = new Connect4();
    }


    @Test
    void testPrintBoard() {

        int board[][] = new int[game.HEIGHT][game.WIDTH];
        board[5][0] = 1; // Set a piece for player 1 in the bottom left corner
        char symbols[] = { '1', '2' };

        String expectedOutput = " 0 1 2 3 4 5 6 7\n" +
                "----------------\n" +
                "| | | | | | | | |\n" +
                "| | | | | | | | |\n" +
                "| | | | | | | | |\n" +
                "| | | | | | | | |\n" +
                "| | | | | | | | |\n" +
                "|1| | | | | | | |\n" +
                "----------------";


        game.printBoard(board, symbols);

        String actualOutput = out.toString().stripTrailing()
                        .replaceAll("(?m)[ \\t]+$", "")  // replace trailing spaces and tabs at the end of each line
                        .replaceAll("\r\n","\n");       // replace Windows line endings with Unix line endings

        assertEquals(expectedOutput, actualOutput, "The printed board does not match the expected output.");
    }


    @Test
    void testCheck() {
        int[][] testBoard01 = { { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 1, 1, 0, 0, 0, 0, 0 } };
        int[][] testBoard02 = { { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 0, 0, 0, 0, 0, 0, 0 } };
        int[][] testBoard03 = { { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 1 },
                { 0, 0, 0, 0, 0, 0, 0, 1 },
                { 0, 0, 0, 0, 0, 2, 2, 1 } };

        assertTrue(game.check(1, testBoard01), "Player 1 should win with a horizontal line.");
        assertTrue(game.check(1, testBoard02 ), "Player 1 should win with a vertical line at col 0.");
        assertTrue(game.check(1, testBoard03), "Player 1 should win with a vertical line at col 7.");

    }

    @Test
    void testNoCheck() {
        int[][] testBoard08 = { { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 1, 0, 0, 0, 0, 0, 0 } };
        int[][] testBoard09 = { { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 1, 2, 1, 0, 0, 0, 0 } };


        assertFalse(game.check(1, testBoard08), "Player 1 should not win with only two pieces.");
        assertFalse(game.check(1, testBoard09), "Player 1 should not win with a vertical line of two pieces.");
    }

    @Test
    void testCheckMate() {
        int[][] testBoard01 = {{0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 2, 2, 0, 0, 0, 0, 0},
                {1, 2, 2, 0, 0, 0, 0, 0},
                {1, 1, 1, 0, 0, 0, 0, 0}};
        assertTrue(game.checkMate(1,testBoard01 ), "Player 1 should win with a horizontal line of three pieces.");
        int[][] testBoard02 = {{0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 0, 0, 0},
                {1, 2, 2, 0, 0, 0, 0, 0},
                {1, 2, 2, 0, 0, 0, 0, 0},
                {1, 1, 1, 0, 0, 0, 0, 0}};

        assertFalse(game.checkMate(1, testBoard02), "Player 1 is not checkmate");


    }

    @Test
    void testValid() {
        int[][] board = new int[game.HEIGHT][game.WIDTH];
        for (int i = 0; i < game.HEIGHT; i++) {
            board[i][0] = 1;
        }
        for (int i = 0; i < 10; i++) {
            assertTrue(game.validate('5', board), "Column 5 should be valid when it's empty.");
        }
        assertFalse(game.validate('0', board), "Column 0 should be invalid when it's full.");
        assertFalse(game.validate((char)1, board), "Should not use int 1 but should use char '1' for column validation.");
    }
}