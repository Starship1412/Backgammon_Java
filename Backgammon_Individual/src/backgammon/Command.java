package backgammon;

/**
 * This class stores and performs syntax checking on a command.
// Requires Eclipse 22.09 or later
 */

public class Command {
	
	public enum CommandType { // Set it as a public class just because the Test class needs it.
		ROLL,
		MOVE,
		QUIT,
		START,
		WAIVE,
		PIP,
		HINT,
		SHOWALLALLOWEDMOVES,
		JUMP,
		SETFACE
	}
	
	private CommandType commandType;
	private String moveFrom, moveTo;
	private String[] dice;
	private int[] faces;
	private static String[] allowedMoves = new String[99];
	private String inputFormattedUpper;
	
	Command (String input) { // Constructor: Initializes a new command object by parsing the input string.
		this.faces = new int[2];
		this.dice = new String[2];
		
		inputFormattedUpper = input.trim().toUpperCase();
		if (inputFormattedUpper.equals("Q")) {
			commandType = CommandType.QUIT;
		} else if (inputFormattedUpper.equals("R")) {
			commandType = CommandType.ROLL;
		} else if (inputFormattedUpper.equals("S")) {
			commandType = CommandType.START;
		} else if (inputFormattedUpper.equals("W")) {
			commandType = CommandType.WAIVE;
		} else if (inputFormattedUpper.equals("P")) {
			commandType = CommandType.PIP;
		} else if (inputFormattedUpper.equals("H")) {
			commandType = CommandType.HINT;
		} else if (inputFormattedUpper.equals("M")) {
			commandType = CommandType.SHOWALLALLOWEDMOVES;
		} else if (inputFormattedUpper.equals("J")) {
			commandType = CommandType.JUMP;
		} else if (inputFormattedUpper.matches("R[1-6][1-6]")) {
			commandType = CommandType.SETFACE;
			dice[0] = inputFormattedUpper.substring(1, 2);
			dice[1] = inputFormattedUpper.substring(2, 3);
			faces[0] = Integer.parseInt(dice[0]);
			faces[1] = Integer.parseInt(dice[1]);
		} else if (inputFormattedUpper.matches("(0[1-9]|1[0-9]|2[0-4]|B[1-2])(0[1-9]|1[0-9]|2[0-4]|E[1-2])")) {
			commandType = CommandType.MOVE;
			moveFrom = inputFormattedUpper.substring(0, 2);
			moveTo = inputFormattedUpper.substring(2, 4);
		} else if (input.matches("([1-9]|0[1-9]|[1-9][0-9])") && allowedMoves[Integer.parseInt(input) - 1] != null) {
            commandType = CommandType.MOVE;
            moveFrom = allowedMoves[Integer.parseInt(input) - 1].substring(0, 2);
            moveTo = allowedMoves[Integer.parseInt(input) - 1].substring(2, 4);
		}
	}
	
	private int suitToBar (String character) { // Converts a bar character to its corresponding index value.
		return switch (character) {
			case "B1" -> 0;
			case "B2" -> 1;
			default -> 0;
		};
	}
	
	private int suitToEndpoint (String character) { // Converts an endpoint character to its corresponding index value.
		return switch (character) {
			case "E1" -> 0;
			case "E2" -> 1;
			default -> 0;
		};
	}
	
	
	
	public static boolean isValid (String input) { // Checks if the input string represents a valid command.
		String inputFormattedUpper = input.trim().toUpperCase();
		return inputFormattedUpper.equals("Q") || inputFormattedUpper.equals("R") || inputFormattedUpper.equals("S") || inputFormattedUpper.equals("W") || inputFormattedUpper.equals("P") || inputFormattedUpper.equals("H") || inputFormattedUpper.equals("M") || inputFormattedUpper.equals("J")
				|| inputFormattedUpper.matches("R[1-6][1-6]") || inputFormattedUpper.matches("(0[1-9]|1[0-9]|2[0-4]|B[1-2])(0[1-9]|1[0-9]|2[0-4]|E[1-2])") || input.matches("([1-9]|0[1-9]|[1-9][0-9])") && allowedMoves[Integer.parseInt(input) - 1] != null;
	}
	
	public static boolean isText (String input) { // Checks if the input string represents a text command.
		String inputFormatted = input.trim();
		return inputFormatted.matches("test:(.+\\.txt)");
	}
	
	public static String getText(String input) { // Retrieves the text from the input string if it's a text command.
	    String inputFormatted = input.trim();
	    if (inputFormatted.length() > 5) {
	        return inputFormatted.substring(5);
	    } else 
	        return ""; // Only for Test.
	}
	
	public boolean isQuit () { // Checks if the command is a QUIT command.
		return commandType == CommandType.QUIT;
	}
	
	public boolean isRoll () { // Checks if the command is a ROLL command.
		return commandType == CommandType.ROLL;
	}
	
	public boolean isStart () { // Checks if the command is a START command.
		return commandType == CommandType.START;
	}
	
	public boolean isWaive () { // Checks if the command is a WAIVE command.
		return commandType == CommandType.WAIVE;
	}
	
	public boolean isShowPips () { // Checks if the command is a PIP command.
		return commandType == CommandType.PIP;
	}
	
	public boolean isShowHint () { // Checks if the command is a HINT command.
		return commandType == CommandType.HINT;
	}
	
	public boolean isShowAllAllowedMoves () { // Checks if the command is a SHOWALLALLOWEDMOVES command.
		return commandType == CommandType.SHOWALLALLOWEDMOVES;
	}
	
	public boolean isJump () { // Checks if the command is a JUMP command.
		return commandType == CommandType.JUMP;
	}
	
	public boolean isSetFace () { // Checks if the command is a SETFACE command.
		return commandType == CommandType.SETFACE;
	}
	
	public boolean isMove () { // Checks if the command is a MOVE command.
		return commandType == CommandType.MOVE;
	}
	
	public boolean isMoveFromBar () { // Checks if the move is from a bar.
		return moveFrom.matches("B1|B2");
	}
	
	public boolean isMoveFromLane () { // Checks if the move is from a lane.
		return moveFrom.matches("0[1-9]|1[0-9]|2[0-4]");
	}
	
	public boolean isMoveToLane () { // Checks if the move is to a lane.
		return moveTo.matches("0[1-9]|1[0-9]|2[0-4]");
	}
	
	public boolean isMoveToEndpoint () { // Checks if the move is to an endpoint.
		return moveTo.matches("E1|E2");
	}
	
	public int getFromIndex () { // Returns the index of the starting position for the move.
		if (isMoveFromLane())
			return Integer.parseInt(moveFrom) - 1;
		else // isMoveFromBar()
			return suitToBar(moveFrom);
	}
	
	public int getToIndex () { // Returns the index of the destination position for the move.
		if (isMoveToLane())
			return Integer.parseInt(moveTo) - 1;
		else // isMoveToEndpoint()
			return suitToEndpoint(moveTo);
	}
	
	public int getFaceInput (int index) { // Returns the specified face value (1 or 2) if the command is a SETFACE command.
		return switch (index) {
			case 1 -> faces[0];
			case 2 -> faces[1];
			default -> 0;
		};
	}
	
	public static void setAllowedMoves (int index, String moveIndex) { // Sets an allowed move at the specified index in the allowedMoves array.
		allowedMoves[index] = moveIndex;
	}
	
	public static String[] getAllowedMoves () { // Returns the array of allowed moves.
		return allowedMoves;
	}
	
	public CommandType getCommandType () { // Only for Test. Returns the CommandType of the command object.
	    return commandType;
	}
	
	public String toString() { // Only for Test. Returns a string representation of the command object.
        return inputFormattedUpper;
    }
}
