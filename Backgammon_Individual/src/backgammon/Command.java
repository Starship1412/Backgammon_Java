package backgammon;

/**
 * This class stores and performs syntax checking on a command.
// Requires Eclipse 22.09 or later
 */

public class Command {
	
	private enum CommandType {
		ROLL,
		MOVE,
		QUIT,
		START,
		WAIVE,
		PIP,
		HINT,
		SETFACE,
		TEXT
	}
	
	private CommandType commandType;
	private String moveFrom, moveTo, face1, face2;
	private int[] face;
	
	Command (String input) {
		this.face = new int[2];
		
		String inputFormattedUpper = input.trim().toUpperCase();
		String inputFormatted = input.trim();
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
		} else if (inputFormattedUpper.matches("R[1-6][1-6]")) {
			commandType = CommandType.SETFACE;
			face1 = inputFormattedUpper.substring(1, 2);
			face2 = inputFormattedUpper.substring(2, 3);
			face[0] = Integer.parseInt(face1);
			face[1] = Integer.parseInt(face2);
		} else if (inputFormattedUpper.matches("(0[1-9]|1[0-9]|2[0-4]|B[1-2])(0[1-9]|1[0-9]|2[0-4]|E[1-2])")) {
			commandType = CommandType.MOVE;
			moveFrom = inputFormattedUpper.substring(0, 2);
			moveTo = inputFormattedUpper.substring(2, 4);
		} else if (inputFormatted.matches("test:(.+\\.txt)")) {
			commandType = CommandType.TEXT;
		}
	}
	
	private int suitToBar (String character) {
		return switch(character) {
			case "B1" -> 0;
			case "B2" -> 1;
			default -> 0;
		};
	}
	
	private int suitToEndpoint (String character) {
		return switch(character) {
			case "E1" -> 0;
			case "E2" -> 1;
			default -> 0;
		};
	}
	
	
	
	public static boolean isValid (String input) {
		String inputFormattedUpper = input.trim().toUpperCase();
		String inputFormatted = input.trim();
		return inputFormattedUpper.equals("Q") || inputFormattedUpper.equals("R") || inputFormattedUpper.equals("S") || inputFormattedUpper.equals("W") || inputFormattedUpper.equals("P") || inputFormattedUpper.equals("H") 
				|| inputFormattedUpper.matches("R[1-6][1-6]") || inputFormattedUpper.matches("(0[1-9]|1[0-9]|2[0-4]|B[1-2])(0[1-9]|1[0-9]|2[0-4]|E[1-2])") || inputFormatted.matches("test:(.+\\.txt)");
	}
	
	public boolean isQuit () {
		return commandType == CommandType.QUIT;
	}
	
	public boolean isRoll () {
		return commandType == CommandType.ROLL;
	}
	
	public boolean isStart () {
		return commandType == CommandType.START;
	}
	
	public boolean isWaive () {
		return commandType == CommandType.WAIVE;
	}
	
	public boolean isShowPips () {
		return commandType == CommandType.PIP;
	}
	
	public boolean isShowHint () {
		return commandType == CommandType.HINT;
	}
	
	public boolean isSetFace () {
		return commandType == CommandType.SETFACE;
	}
	
	public boolean isMove () {
		return commandType == CommandType.MOVE;
	}
	
	public static boolean isText (String input) {
		String inputFormatted = input.trim();
		return inputFormatted.matches("test:(.+\\.txt)");
	}
	
	public static String getText (String input) {
		String inputFormatted = input.trim();
		return inputFormatted.substring(5);
	}
	
	public boolean isMoveFromBar () {
		return moveFrom.matches("B1|B2");
	}
	
	public boolean isMoveFromLane () {
		return moveFrom.matches("0[1-9]|1[0-9]|2[0-4]");
	}
	
	public boolean isMoveToLane () {
		return moveTo.matches("0[1-9]|1[0-9]|2[0-4]");
	}
	
	public boolean isMoveToEndpoint () {
		return moveTo.matches("E1|E2");
	}
	
	public int getFromIndex () {
		if (isMoveFromLane())
			return Integer.parseInt(moveFrom) - 1;
		else // isMoveFromBar()
			return suitToBar(moveFrom);
	}
	
	public int getToIndex () {
		if (isMoveToLane())
			return Integer.parseInt(moveTo) - 1;
		else // isMoveToEndpoint()
			return suitToEndpoint(moveTo);
	}
	
	public int getFaceInput (int index) {
		return switch (index) {
			case 1 -> face[0];
			case 2 -> face[1];
			default -> 0;
		};
	}
}
