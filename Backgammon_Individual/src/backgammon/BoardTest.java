package backgammon;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {
	
    private Board board;
    private Command command;

    @BeforeEach
    void setUp() {
        board = new Board();
        board.setPlayer(1, new Player("Player 1", PieceEntity.R)); // Initialize players with the virtual inputs
	    board.setPlayer(2, new Player("Player 2", PieceEntity.W));
	    board.setCurrentPlayer(1);
	    command = new Command("R12"); // Create a command with 2 dice numbers as specified for the move
        board.setDiceFace(command); // Set the dice face values
        board.initializeBoard();
        board.setMatchNumber(3);
    }

    @Test // Test if the board object is created
    void testBoard() {
        assertNotNull(board);
    }

    @Test // Test if the board object is created with an InputStream
    void testBoardInputStream() {
        InputStream inputStream = new ByteArrayInputStream("Player 1\nPlayer 2\n5\n".getBytes());
        Board boardWithInputStream = new Board(inputStream);
        assertNotNull(boardWithInputStream);
    }

    @Test // Test if the players are initialized correctly
    void testInitializePlayer() {
    	InputStream inputStream = new ByteArrayInputStream("Player 1\nPlayer 2\n".getBytes(StandardCharsets.UTF_8));
		board = new Board(inputStream);
        board.initializePlayer(1);
        assertNotNull(board.getPlayer(1));
        assertEquals(PieceEntity.R, board.getPlayer(1).getPieceEntity());
        board.initializePlayer(2);
        assertNotNull(board.getPlayer(2));
        assertEquals(PieceEntity.W, board.getPlayer(2).getPieceEntity());
    }

    @Test // Test if the end turn function updates the current player correctly
	void testEndTurn() {
		board.endTurn();
		assertEquals(board.getPlayer(0), board.getPlayer(2));
	}

    @Test // Test if the board is initialized correctly
    void testInitializeBoard() {
        assertEquals(2, board.getLane(0).size());
        assertEquals(PieceEntity.W, board.getLane(0).peek().getPieceEntity());
        assertEquals(3, board.getLane(7).size());
        assertEquals(PieceEntity.R, board.getLane(7).peek().getPieceEntity());
        assertEquals(5, board.getLane(12).size());
        assertEquals(PieceEntity.R, board.getLane(12).peek().getPieceEntity());
    }

    @Test // Test if a move is possible based on the given command
    void testMoveIsPossible() {
        command = new Command("2423");
        assertTrue(board.moveIsPossible(command));
    }

    @Test // Test move() method
    void testMove() {
        command = new Command("2423");
        board.move(command); // Perform the move
        assertEquals(1, board.getLane(22).size());
        assertEquals(1, board.getLane(23).size());
    }

    @Test // Test setDiceFace() method
    void testSetDiceFace() {
        assertEquals(1, board.getDiceFace(1)); // Assert the expected result
        assertEquals(2, board.getDiceFace(2));
    }

    @Test // Test getDiceFace() method
    void testGetDiceFace() {
        assertEquals(1, board.getDiceFace(1)); // Assert the expected result
        assertEquals(2, board.getDiceFace(2));
    }

    @Test // Test isOneMatchOver() method
    void testIsOneMatchOver() {
    	for (int i = 0; i < 24; i++)
	        board.getLane(i).clear();
	    for (int i = 0; i < 2; i++)
	    	board.getBar(i).clear();
	    for (int i = 0; i < 2; i++)
	    	board.getEndpoint(i).clear();
		for (int i = 0; i < 15; i++)
			board.getLane(20).push(new Piece(PieceEntity.W));
		for (int i = 0; i < 15; i++)
			board.getEndpoint(0).push(new Piece(PieceEntity.R));
		assertEquals(15, board.getEndpoint(0).size());
    }

    @Test // Test isWholeMatchOver() method
    void testIsWholeMatchOver() {
    	for (int i = 0; i < 3; i++)
    		board.addMatchRoundNumber();
    	assertEquals(board.getMatchNumber() + 1, board.getMatchRoundNumber());
    }

    @Test // Test getSize() method
    void testGetSize() {
        assertEquals(5, board.getSize("upLane")); // Assert the expected result for upLane and downLane
        assertEquals(5, board.getSize("downLane"));
    }

    @Test // Test calculateSetPips() method
    void testCalculateSetPips() {
        command = new Command("2423");
        board.move(command); // Perform the move
        command = new Command("2422");
        board.move(command);
        board.calculateSetPips(); // Calculate the set pips
        assertEquals(164, board.getPlayer(1).getPips()); // Assert the expected result
        assertEquals(167, board.getPlayer(2).getPips());
    }

    @Test // Test if the dice roll generates a non-zero dice move number
    void testMakeDiceRoll() {
        board.makeDiceRoll();
        assertNotEquals(0, board.getDiceMoveNumber());
    }

    @Test // Test if the sum of the dice move steps is returned correctly
    void testGetDiceMoveNumber() {
        board.setDiceMoveStep(3, 4);
        assertEquals(7, board.getDiceMoveNumber());
    }

    @Test // Test if the dice move number is set to zero
    void testMakeDiceSetZero() {
        board.makeDiceSetZero();
        assertEquals(0, board.getDiceMoveNumber());
    }

    @Test // Test if the correct player is returned
    void testGetPlayer() {
        Player player1 = board.getPlayer(1);
        assertEquals(DisplayColour.RED + "Player 1" + DisplayColour.RESET, player1.getNamewithColor());
    }

    @Test // Test if the current player is set correctly
    void testSetCurrentPlayer() {
        board.setCurrentPlayer(2);
        assertEquals(DisplayColour.WHITE + "Player 2" + DisplayColour.RESET, board.getPlayer(0).getNamewithColor());
    }

    @Test // Test if a valid lane is returned
    void testGetLane() {
        assertNotNull(board.getLane(1));
    }

    @Test // Test if a valid bar is returned
    void testGetBar() {
        assertNotNull(board.getBar(1));
    }

    @Test // Test if a valid endpoint is returned
    void testGetEndpoint() {
        assertNotNull(board.getEndpoint(1));
    }

    @Test // Test if the correct match number is returned
    void testGetMatchNumber() {
        assertEquals(3, board.getMatchNumber());
    }

    @Test // Test if the match number is set correctly
    void testSetMatchNumber() {
        board.setMatchNumber(5);
        assertEquals(5, board.getMatchNumber());
    }

    @Test // Test if the correct match round number is returned
    void testGetMatchRoundNumber() {
        board.setMatchRoundNumber(2);
        assertEquals(2, board.getMatchRoundNumber());
    }

    @Test // Test if the match round number is set correctly
    void testSetMatchRoundNumber() {
        board.setMatchRoundNumber(3);
        assertEquals(3, board.getMatchRoundNumber());
    }

    @Test // Test if the match round number is incremented correctly
    void testAddMatchRoundNumber() {
        board.setMatchRoundNumber(1);
        board.addMatchRoundNumber();
        assertEquals(2, board.getMatchRoundNumber());
    }

    @Test // Test if the players' scores are set to zero
    void testSetPlayersScoreToZero() {
        board.setPlayersScoreToZero();
        assertEquals(0, board.getPlayer(1).getScore());
        assertEquals(0, board.getPlayer(2).getScore());
    }

    @Test // Test if the current player's score is incremented correctly
    void testAddCurrentPlayerScore() {
        board.addCurrentPlayerScore();
        assertEquals(10, board.getPlayer(0).getScore());
    }

    @Test // Test if the correct dice move steps are returned
    void testGetDiceMoveStep() {
        board.setDiceMoveStep(4, 0); // Set the dice move steps
        assertEquals(4, board.getDiceMoveStep(1)); // Test if the dice move steps are returned correctly
        assertEquals(0, board.getDiceMoveStep(2));
    }

    @Test // Test if the dice move steps are set correctly
    void testSetDiceMoveStep() {
        board.setDiceMoveStep(4, 0); // Set the dice move steps
        assertEquals(4, board.getDiceMoveNumber()); // Test if the dice move steps are set correctly
    }

    @Test // Test if the new player is set correctly
    void testSetPlayer() {
        Player newPlayer = new Player("New Player", PieceEntity.R); // Set a new player for index 1
        board.setPlayer(1, newPlayer);
        assertEquals(DisplayColour.RED + "New Player" + DisplayColour.RESET, board.getPlayer(1).getNamewithColor()); // Test if the new player at index 1 is updated correctly
    }
}
