package backgammon;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;

class ViewTest {

	private View view;
	private Board board;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    // Set up method to initialize View instance and output stream
    void setUp() throws Exception {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        view = new View();
    }

    @Test
    // Test View constructor
    void testView() {
        assertNotNull(view);
    }

    @Test
    // Test displayWelcome() method
    void testDisplayWelcome() {
        view.displayWelcome();
        String expectedOutput = "Welcome to Backgammon\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    // Test displayStart() method
    void testDisplayStart() {
        view.displayStart();
        String expectedOutput = "Enter S to start the game or restart the game. Enter Q to exit the game. Enter H to view all allowed commands.\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    // Test displayRestart() method
    void testDisplayRestart() {
        view.displayRestart();
        String expectedOutput = "Restarting the game...\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    // Test displayPiece() method
    void testDisplayPiece() {
        view.displayPiece(board);
        String expectedOutput = "Player 1's piece\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    // Test getUserInput() method
    void testGetUserInput() {
        ByteArrayInputStream inContent = new ByteArrayInputStream("test input\n".getBytes());
        System.setIn(inContent);
        Command userInput = view.getUserInput(board);
        assertEquals("test input", userInput);
    }

    @Test
    // Test showAllAllowedMoves() method
    void testShowAllAllowedMoves() {
        ArrayList<String> moves = new ArrayList<>(Arrays.asList("1-3", "1-4", "2-3"));
        view.showAllAllowedMoves(moves);
        String expectedOutput = "Allowed moves: 1-3, 1-4, 2-3\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    // Test firstDiceRoll() method
    void testFirstDiceRoll() {
        view.firstDiceRoll(1, 4);
        String expectedOutput = "First dice roll: Player 1 rolls 1, Player 2 rolls 4. Player 2 goes first.\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    // Test getStartInformation() method
    void testGetStartInformation() {
        ByteArrayInputStream inContent = new ByteArrayInputStream("Player 1\nPlayer 2\n".getBytes());
        System.setIn(inContent);
        String[] playerNames = view.getStartInformation();
        assertArrayEquals(new String[]{"Player 1", "Player 2"}, playerNames);
    }

    @Test
    // Test displayCommandNotPossible() method
    void testDisplayCommandNotPossible() {
        view.displayCommandNotPossible("test command");
        String expectedOutput = "Command 'test command' is not possible. Please try again.\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    // Test displayCommandTemporarilyInvalid() method
    void testDisplayCommandTemporarilyInvalid() {
        view.displayCommandTemporarilyInvalid("test command");
        String expectedOutput = "Command 'test command' is temporarily invalid. Please try again later.\n";
        assertEquals(expectedOutput, outContent.toString());
    }

	@Test
	void testDisplayOneMatchOver() {
		fail("Not yet implemented");
	}

	@Test
	void testDisplayForceJump() {
		fail("Not yet implemented");
	}

	@Test
	void testDisplayWholeMatchOver() {
		fail("Not yet implemented");
	}

	@Test
	void testDisplayQuit() {
		fail("Not yet implemented");
	}

	@Test
	void testPlayerTurnCurrent() {
		fail("Not yet implemented");
	}

	@Test
	void testPlayerTurnNext() {
		fail("Not yet implemented");
	}

	@Test
	void testShowDice() {
		fail("Not yet implemented");
	}

	@Test
	void testDisplayWaive() {
		fail("Not yet implemented");
	}

	@Test
	void testDisplayPips() {
		fail("Not yet implemented");
	}

	@Test
	void testReadContentFromFile() {
		fail("Not yet implemented");
	}

	@Test
	void testShowHint() {
		fail("Not yet implemented");
	}

}
