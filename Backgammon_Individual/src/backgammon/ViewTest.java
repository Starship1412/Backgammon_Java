package backgammon;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

class ViewTest {

	private View view;
	private Board board;
	private Command command;

	@BeforeEach
	void setUp() throws Exception {
	    view = new View();
	    board = new Board();
	    board.setPlayer(1, new Player("Player 1", PieceEntity.R)); // Initialize players with the virtual inputs
	    board.setPlayer(2, new Player("Player 2", PieceEntity.W));
	    board.setCurrentPlayer(1);
	    command = new Command("R12"); // Create a command with 2 dice numbers as specified for the move
        board.setDiceFace(command); // Set the dice face values
        board.initializeBoard();
        board.setMatchNumber(3);
	}

    @Test // Test the View constructor
    void testView() {
        assertNotNull(view);
    }

    @Test // Test the displayWelcome method
    void testDisplayWelcome() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        view.displayWelcome();
        assertEquals("Welcome to Backgammon\n", outputStream.toString());
    }

    @Test // Test the displayStart method
    void testDisplayStart() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        view.displayStart();
        assertEquals("Enter S to start the game or restart the game. Enter Q to exit the game. Enter H to view all allowed commands.\nNote that some commands are temporarily invalid in some cases.\n", outputStream.toString());
    }

    @Test // Test the displayRestart method
    void testDisplayRestart() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        view.displayRestart();
        assertEquals("The game starts over from the beginning!\n", outputStream.toString());
    }

    @Test // Test the displayPiece method
    void testDisplayPiece() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        view.displayPiece(board);
        String expectedOutput = """
				|---------------------------------------------------------------------|---------------|
				| Current player's color: \u001b[1;31mRED\u001b[0m                               pips: 167 |   Match: 3    |
				|---------------------------------------------------------------------|---------------|
				| Dice:                     1              2                          |   Round: 1    |
				|---------------------------------------------------------------------|---------------|
				| \u001b[0;37m13   14   15   16   17   18\u001b[0m | \u001b[0;37mB2\u001b[0m | \u001b[0;37m19   20   21   22   23   24\u001b[0m | \u001b[0;31mE1\u001b[0m |   \u001b[1;31mRED\u001b[0m Score   |
				| \u001b[0;31m12   11   10   09   08   07\u001b[0m | \u001b[0;37mB2\u001b[0m | \u001b[0;31m06   05   04   03   02   01\u001b[0m | \u001b[0;31mE1\u001b[0m |       0       |
				| \u001b[0;37m ●\u001b[0m                  \u001b[0;31m ●\u001b[0m      |    | \u001b[0;31m ●\u001b[0m                       \u001b[0;37m ●\u001b[0m | \u001b[1;31m00\u001b[0m |---------------|
				| \u001b[0;37m ●\u001b[0m                  \u001b[0;31m ●\u001b[0m      |    | \u001b[0;31m ●\u001b[0m                       \u001b[0;37m ●\u001b[0m |    |
				| \u001b[0;37m ●\u001b[0m                  \u001b[0;31m ●\u001b[0m      |    | \u001b[0;31m ●\u001b[0m                          |    |
				| \u001b[0;37m ●\u001b[0m                          |    | \u001b[0;31m ●\u001b[0m                          |    |
				| \u001b[0;37m ●\u001b[0m                          |    | \u001b[0;31m ●\u001b[0m                          |    |
				|-----------------------------|----|-----------------------------|----|
				| \u001b[0;31m ●\u001b[0m                          |    | \u001b[0;37m ●\u001b[0m                          |    |
				| \u001b[0;31m ●\u001b[0m                          |    | \u001b[0;37m ●\u001b[0m                          |    |
				| \u001b[0;31m ●\u001b[0m                  \u001b[0;37m ●\u001b[0m      |    | \u001b[0;37m ●\u001b[0m                          |    |
				| \u001b[0;31m ●\u001b[0m                  \u001b[0;37m ●\u001b[0m      |    | \u001b[0;37m ●\u001b[0m                       \u001b[0;31m ●\u001b[0m |    |
				| \u001b[0;31m ●\u001b[0m                  \u001b[0;37m ●\u001b[0m      |    | \u001b[0;37m ●\u001b[0m                       \u001b[0;31m ●\u001b[0m | \u001b[1;37m00\u001b[0m |---------------|
				| \u001b[0;37m12   11   10   09   08   07\u001b[0m | \u001b[0;31mB1\u001b[0m | \u001b[0;37m06   05   04   03   02   01\u001b[0m | \u001b[0;37mE2\u001b[0m |  \u001b[1;37mWHITE\u001b[0m Score  |
				| \u001b[0;31m13   14   15   16   17   18\u001b[0m | \u001b[0;31mB1\u001b[0m | \u001b[0;31m19   20   21   22   23   24\u001b[0m | \u001b[0;37mE2\u001b[0m |       0       |
				|---------------------------------------------------------------------|---------------|
        		""";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test // Test the getUserInput method
    void testGetUserInput() {
        String input = "Q\n"; // Prepare input stream with simulated user input
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        View view = new View(); // Instantiate View class
        Command command = view.getUserInput(board); // Call the getUserInput method
        assertEquals("Q", command.toString()); // Assert that the returned command is "Q" as expected
    }

    @Test
    void testShowAllAllowedMoves() { // This test checks if the showAllAllowedMoves() function outputs the correct allowed moves.
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        view.showAllAllowedMoves(board);
        String expectedOutput = """
        		Here are the moves you can make:
        		 1: 2423
        		 2: 2422
        		 3: 2421
        		 4: 1311
        		 5: 1310
        		 6: 0807
        		 7: 0806
        		 8: 0805
        		 9: 0605
        		10: 0604
        		11: 0603
        		"""; // The expected output for the scenario
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testFirstDiceRoll() { // This test checks if the FirstDiceRoll() function changes the board state as expected.
        board.setDiceFace(command); // Set up a scenario for testing FirstDiceRoll()
        view.FirstDiceRoll(board);
        assertTrue(board.getDiceFace(1) != 0 && board.getDiceFace(2) != 0); // Check if the board state has changed as expected
    }

    @Test
    void testGetStartInformation() { // This test checks if the getStartInformation() function correctly reads the provided input and sets the match number.
        String input = "Player 1\nPlayer 2\n5\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        View view = new View(inputStream);
        String inputPlayer = "\n\n"; // Provide virtual inputs
        InputStream in = new ByteArrayInputStream(inputPlayer.getBytes());
        Board board = new Board(in); // Create a new Board instance with the provided input stream
        System.setIn(in);
        view.getStartInformation(board);
        int expectedMatchNumber = 5;
        assertEquals(expectedMatchNumber, board.getMatchNumber());
    }

    @Test
    void testDisplayCommandNotPossible() { // This test checks if the displayCommandNotPossible() function outputs the correct message.
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        view.displayCommandNotPossible();
        String expectedOutput = "That play is not possible. Try again.\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testDisplayCommandTemporarilyInvalid() { // This test checks if the displayCommandTemporarilyInvalid() function outputs the correct message.
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        view.displayCommandTemporarilyInvalid();
        String expectedOutput = "The command is temporarily invalid. Try again.\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testDisplayOneMatchOver() { // This test checks if the displayOneMatchOver() function outputs the correct message.
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        board.setMatchNumber(5); // Set up a scenario for testing displayOneMatchOver()
        board.setMatchRoundNumber(1);
        view.displayOneMatchOver(board);
        String expectedOutput = "Round 1 of the competition is over. " + DisplayColour.RED + "Player 1" + DisplayColour.RESET + " wins the current match round.\n"
                + "There are 5 rounds in total, so there are still 4 rounds left to play. Please press J to play the next round.\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testDisplayForceJump() { // This test checks if the displayForceJump() function outputs the correct message.
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        view.displayForceJump(board);
        String expectedOutput = "The current match round was forced to end early. No player accumulates points because of this.\nThere are 3 rounds in total, so there are still 2 rounds left to play. The next match round has already begun.\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testDisplayWholeMatchOver() { // This test checks if the displayWholeMatchOver() function outputs the correct message.
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        view.displayWholeMatchOver(board);
        String expectedOutput = "the two players play to a draw.\nGame over.\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testDisplayQuit() { // This test checks if the displayQuit() function outputs the correct message.
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        view.displayQuit();
        String expectedOutput = "Quit.\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testPlayerTurnCurrent() { // This test checks if the playerTurnCurrent() function outputs the correct message.
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        view.playerTurnCurrent(board.getPlayer(1));
        String expectedOutput = DisplayColour.RED + "Player 1" + DisplayColour.RESET + "<167>pips(RED) finishes moving.\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testPlayerTurnNext() { // This test checks if the playerTurnNext() function outputs the correct message.
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        view.playerTurnNext(board.getPlayer(2));
        String expectedOutput = "Now it's the " + DisplayColour.WHITE + "Player 2" + DisplayColour.RESET + "<167>pips(WHITE)'s turn to play.\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testShowDice() { // This test checks if the showDice() function outputs the correct message.
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        view.showDice(2, 3);
        String expectedOutput = "The number of 2 dice thrown are 2 and 3. So please move 2 times.\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testDisplayWaive() { // This test checks if the displayWaive() function outputs the correct message.
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        view.displayWaive(board.getPlayer(1));
        String expectedOutput = DisplayColour.RED + "Player 1" + DisplayColour.RESET + "<167>pips forfeits the turn as he has no pieces to play legally or he doesn't know how to move the pieces further. Now skip to the other player's turn.\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testDisplayPips() { // This test checks if the displayPips() function outputs the correct message.
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        view.displayPips(board);
        String expectedOutput = DisplayColour.RED + "Player 1" + DisplayColour.RESET + "'s current pips are 167.\n" + DisplayColour.WHITE + "Player 2" + DisplayColour.RESET + "'s current pips are 167.\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testReadContentFromFile() throws IOException { // This test checks if the readContentFromFile() function correctly reads the content from the provided file.
        Path tempFile = Files.createTempFile("", ".txt"); // Create a temporary file with some content
        String fileContent = "J\n";
        Files.write(tempFile, fileContent.getBytes());
        String inputString = tempFile.toString() + "\n"; // Create a Scanner with the temporary file's path as input
        Scanner scanner = new Scanner(new ByteArrayInputStream(inputString.getBytes())); // Read the content from the temporary file
        String promptMessage = "Please enter the file name: ";
        String actualContent = view.readContentFromFile("test:M.txt", scanner, promptMessage); // M.txt is a file located locally on my computer.
        assertEquals(fileContent.replaceAll("\\s+", " ").trim(), actualContent.replaceAll("\\s+", " ").trim()); // Compare the expected content with the actual content read from the file after normalizing strings
        Files.delete(tempFile); // Delete the temporary file
        scanner.close(); // Close the scanner
    }

	@Test
	void testShowHint() { // This test checks if the showHint() function outputs the correct message.
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        view.showHint();
        String expectedOutput = """
        		S: Start Backgammon or restart Backgammon.
        		R: Roll the dice.
        		R + 1 digit + 1 digit: Roll the dice to get 2 dice numbers as specified.
        		W: Waive current turn.
        		P: View 2 players' pips.
        		2 digits + 2 digits: Move a piece on Lane.
        		B + 1 digit + 2 digits: Move a piece from Bar to Lane.
        		2 digits + E + 1 digit: Move a piece from Lane to Endpoint.
        		H: View all allowed commands.
        		M: View all allowed moves.
        		J: Regardless of whether or not the current match round is completed, the current match round will end and the next match round will be played.
        		Q: Quit the game.
		        If you type \"test:file_name.txt\", the game will read the commands in that file.
        		""";
        assertEquals(expectedOutput, outputStream.toString());
	}
}
