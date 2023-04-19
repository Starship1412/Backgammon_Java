package backgammon;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {
	
	Game game;
	
	@BeforeEach
	void setUp() throws Exception {
		game = new Game();
	}

	@Test
	void testPlayGame(){
	    System.setOut(System.out);
	    String input = "S\nPlayer 1\nPlayer 2\n3\nR24\ntest:globalTest.txt\nQ\n";
	    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
	    System.setIn(inputStream);
	    game = new Game();
	    game.playGame();
	    Board boardCompare = new Board();
		for (int i = 0; i < 24; i++)
	        boardCompare.getLane(i).clear();
	    for (int i = 0; i < 2; i++)
	    	boardCompare.getBar(i).clear();
	    for (int i = 0; i < 2; i++)
	    	boardCompare.getEndpoint(i).clear();
		for (int i = 0; i < 1; i++) {
			boardCompare.getLane(1).push(new Piece(PieceEntity.W));
			boardCompare.getLane(3).push(new Piece(PieceEntity.W));
			boardCompare.getLane(10).push(new Piece(PieceEntity.R));
			boardCompare.getLane(19).push(new Piece(PieceEntity.R));
			boardCompare.getLane(23).push(new Piece(PieceEntity.R));
		}
		for (int i = 0; i < 3; i++) {
			boardCompare.getLane(7).push(new Piece(PieceEntity.R));
			boardCompare.getLane(16).push(new Piece(PieceEntity.W));
		}
		for (int i = 0; i < 4; i++)
			boardCompare.getLane(12).push(new Piece(PieceEntity.R));
		for (int i = 0; i < 5; i++) {
			boardCompare.getLane(5).push(new Piece(PieceEntity.R));
			boardCompare.getLane(11).push(new Piece(PieceEntity.W));
			boardCompare.getLane(18).push(new Piece(PieceEntity.W));
		}
		if (!game.getBoard().equals(boardCompare)) {
			for (int i = 0; i < 24; i++) {
				System.out.print(game.getBoard().getPieceCountAtLane(i) + "   ");
				System.out.println(boardCompare.getPieceCountAtLane(i));
			}
		}
		assertTrue(game.getBoard().equals(boardCompare));
	}
}
