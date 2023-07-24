package backgammon;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
	
    private Player player;
    
    @BeforeEach // Set up the test environment
    void setUp() throws Exception {
        player = new Player("John Doe", PieceEntity.R);
    }
    
    @Test // Test Player constructor
    void testPlayer() {
        assertNotNull(player);
    }
    
    @Test // Test getNamewithColor method
    void testGetNamewithColor() {
        assertEquals("\033[0;31m" + "John Doe" + "\033[0m", player.getNamewithColor());
    }
    
    @Test // Test getPieceEntity method
    void testGetPieceEntity() {
        assertEquals(PieceEntity.R, player.getPieceEntity());
    }
    
    @Test // Test getColourName method
    void testGetColourName() {
        assertEquals("RED", player.getColourName());
    }
    
    @Test // Test getPips method
    void testGetPips() {
        assertEquals(167, player.getPips());
    }
    
    @Test // Test setPips method
    void testSetPips() {
        player.setPips(150);
        assertEquals(150, player.getPips());
    }
    
    @Test // Test getScore method
    void testGetScore() {
        assertEquals(0, player.getScore());
    }
    
    @Test // Test addScore method
    void testAddScore() {
        player.addScore(5);
        assertEquals(5, player.getScore());
    }
    
    @Test // Test setScore method
    void testSetScore() {
        player.setScore(10);
        assertEquals(10, player.getScore());
    }
    
    @Test // Test toString method
    void testToString() {
        assertEquals("\033[0;31m" + "John Doe" + "\033[0m" + "<167>pips", player.toString());
    }
}
