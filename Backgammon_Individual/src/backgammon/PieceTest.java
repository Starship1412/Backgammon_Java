package backgammon;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PieceTest {

    private Piece piece;

    @BeforeEach // Set up a new Piece object before each test
    void setUp() throws Exception {
        piece = new Piece(PieceEntity.R);
    }

    @Test // Test the Piece constructor with a PieceEntity
    void testPiece() {
        assertNotNull(piece);
    }

    @Test // Test if the PieceEntity can be retrieved from a Piece object
    void testGetPieceEntity() {
        assertEquals(PieceEntity.R, piece.getPieceEntity());
    }

    @Test // Test if the toString() method returns the correct string representation
    void testToString() {
        assertEquals(PieceEntity.R.toString(), piece.toString());
    }
}
