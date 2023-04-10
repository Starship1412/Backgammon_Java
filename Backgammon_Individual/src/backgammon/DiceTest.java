package backgammon;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DiceTest {

    private Dice dice;

    @BeforeEach // Set up a new Dice object before each test
    void setUp() throws Exception {
        dice = new Dice();
    }

    @Test // Test the Dice constructor
    void testDice() {
        assertNotNull(dice);
    }

    @Test // Test if the correct face value is returned
    void testGetFace() {
        dice.setFace(1, 4);
        assertEquals(1, dice.getFace(1));
        assertEquals(4, dice.getFace(2));
    }

    @Test // Test the roll method
    void testRoll() {
        dice.roll();
        assertTrue(dice.getFace(1) >= 1 && dice.getFace(1) <= 6);
        assertTrue(dice.getFace(2) >= 1 && dice.getFace(2) <= 6);
    }

    @Test // Test the setZero method
    void testSetZero() {
        dice.setZero();
        assertEquals(0, dice.getFace(1));
        assertEquals(0, dice.getFace(2));
    }

    @Test // Test the setFace method
    void testSetFace() {
        dice.setFace(3, 5);
        assertEquals(3, dice.getFace(1));
        assertEquals(5, dice.getFace(2));
    }

    @Test // Test the getMoveStep method
    void testGetMoveStep() {
        dice.setMoveStep(1, 2);
        assertEquals(1, dice.getMoveStep(1));
        assertEquals(2, dice.getMoveStep(2));
    }

    @Test // Test the setMoveStep method
    void testSetMoveStep() {
        dice.setMoveStep(3, 4);
        assertEquals(3, dice.getMoveStep(1));
        assertEquals(4, dice.getMoveStep(2));
    }

    @Test // Test the getMoveNumber method
    void testGetMoveNumber() {
        dice.setMoveStep(2, 3);
        assertEquals(5, dice.getMoveNumber());
    }

    @Test // Test the subtractMoveStep method
    void testSubtractMoveStep() {
        dice.setMoveStep(2, 3);
        dice.subtractMoveStep(1);
        assertEquals(1, dice.getMoveStep(1));
        assertEquals(3, dice.getMoveStep(2));
    }
}
