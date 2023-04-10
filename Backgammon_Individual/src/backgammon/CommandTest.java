package backgammon;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandTest {
	
	private Command command;

	@BeforeEach
	void setUp() throws Exception {
		command = null;
	}

	@Test // Test if Command object is created with the correct CommandType
	void testCommand() {
		command = new Command("R");
		assertEquals(Command.CommandType.ROLL, command.getCommandType());
	}

	@Test // Test if the input command is valid
	void testIsValid() {
		assertTrue(Command.isValid("R"));
		assertTrue(Command.isValid("Q"));
		assertTrue(Command.isValid("S"));
		assertTrue(Command.isValid("W"));
		assertTrue(Command.isValid("P"));
		assertTrue(Command.isValid("H"));
		assertTrue(Command.isValid("M"));
		assertTrue(Command.isValid("J"));
		assertTrue(Command.isValid("R11"));
		assertTrue(Command.isValid("0109"));
		assertFalse(Command.isValid("InvalidCommand"));
	}

	@Test // Test if command is 'Quit'
	void testIsQuit() {
		command = new Command("Q");
		assertTrue(command.isQuit());
	}

	@Test // Test if command is 'Roll'
	void testIsRoll() {
		command = new Command("R");
		assertTrue(command.isRoll());
	}

	@Test // Test if command is 'Start'
	void testIsStart() {
		command = new Command("S");
		assertTrue(command.isStart());
	}

	@Test // Test if command is 'Waive'
	void testIsWaive() {
		command = new Command("W");
		assertTrue(command.isWaive());
	}

	@Test // Test if command is 'Show Pips'
	void testIsShowPips() {
		command = new Command("P");
		assertTrue(command.isShowPips());
	}

	@Test // Test if command is 'Show Hint'
	void testIsShowHint() {
		command = new Command("H");
		assertTrue(command.isShowHint());
	}

	@Test // Test if command is 'Show All Allowed Moves'
	void testIsShowAllAllowedMoves() {
		command = new Command("M");
		assertTrue(command.isShowAllAllowedMoves());
	}

	@Test // Test if command is 'Jump'
	void testIsJump() {
		command = new Command("J");
		assertTrue(command.isJump());
	}

	@Test // Test if command is 'Set Face'
	void testIsSetFace() {
		command = new Command("R11");
		assertTrue(command.isSetFace());
	}

	@Test // Test if command is 'Move'
	void testIsMove() {
		command = new Command("0109");
		assertTrue(command.isMove());
	}

	@Test // Test if command is 'Move From Bar'
	void testIsMoveFromBar() {
		command = new Command("B102");
		assertTrue(command.isMoveFromBar());
	}

	@Test // Test if command is 'Move From Lane'
	void testIsMoveFromLane() {
		command = new Command("0109");
		assertTrue(command.isMoveFromLane());
	}

	@Test // Test if command is 'Move To Lane'
	void testIsMoveToLane() {
		command = new Command("0109");
		assertTrue(command.isMoveToLane());
	}

	@Test // Test if command is 'Move To Endpoint'
	void testIsMoveToEndpoint() {
		command = new Command("01E1");
		assertTrue(command.isMoveToEndpoint());
	}

	@Test // Test if the 'from' index is correct for the command
	void testGetFromIndex() {
		command = new Command("0109");
		assertEquals(0, command.getFromIndex());
	}

	@Test // Test if the 'to' index is correct for the command
	void testGetToIndex() {
		command = new Command("0109");
		assertEquals(8, command.getToIndex());
	}
}
