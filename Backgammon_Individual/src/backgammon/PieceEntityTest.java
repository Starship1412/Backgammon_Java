package backgammon;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PieceEntityTest {

    @Test // Test if the instance of PieceEntity is created correctly
    void testPieceEntity() {
        assertNotNull(PieceEntity.R);
        assertNotNull(PieceEntity.W);
    }

    @Test // Test if the getDisplay method returns the expected display value
    void testGetDisplay() {
        assertEquals(DisplayColour.RED, PieceEntity.R.getDisplay());
        assertEquals(DisplayColour.WHITE, PieceEntity.W.getDisplay());
    }

    @Test // Test if the getColour method returns the expected color value
    void testGetColour() {
        assertEquals("RED", PieceEntity.R.getColour());
        assertEquals("WHITE", PieceEntity.W.getColour());
    }

    @Test // Test if the toString method returns the expected string representation
    void testToString() {
        assertEquals(DisplayColour.RED + " ●" + DisplayColour.RESET, PieceEntity.R.toString());
        assertEquals(DisplayColour.WHITE + " ●" + DisplayColour.RESET, PieceEntity.W.toString());
    }
}
