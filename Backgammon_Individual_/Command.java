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
		ROLLNUMBER
	}
	
	private CommandType commandType;
	private String moveFrom;
	private String moveTo;
	
	Command (String input) {
		String inputFormatted = input.trim().toUpperCase();
		if (inputFormatted.equals("Q")) {
			commandType = CommandType.QUIT;
		} else if (inputFormatted.equals("R")) {
			commandType = CommandType.ROLL;
		} else if (inputFormatted.equals("S")) {
			commandType = CommandType.START;
		} else if (inputFormatted.equals("W")) {
			commandType = CommandType.WAIVE;
		} else if (inputFormatted.equals("P")) {
			commandType = CommandType.PIP;
		} else if (inputFormatted.equals("H")) {
			commandType = CommandType.HINT;
		} else if (inputFormatted.matches("R[1-6][1-6]")) {
			commandType = CommandType.ROLLNUMBER;
		} else if (inputFormatted.matches("(0[1-9]|1[0-9]|2[0-4]|B[1-2])(0[1-9]|1[0-9]|2[0-4]|T[1-2])")) {
			commandType = CommandType.MOVE;
			moveFrom = inputFormatted.substring(0, 2);
			moveTo = inputFormatted.substring(2, 4);
		} 	
	}
	
	private int suitToBar (String character) {
		return switch(character) {
			case "B1" -> 0;
			case "B2" -> 1;
			default -> 0;
		};
	}
	
	private int suitToTerminus (String character) {
		return switch(character) {
			case "T1" -> 0;
			case "T2" -> 1;
			default -> 0;
		};
	}
	
	
	
	public static boolean isValid (String input) {
		String inputFormatted = input.trim().toUpperCase();
		return inputFormatted.equals("Q") || inputFormatted.equals("R") || inputFormatted.equals("S") || inputFormatted.equals("W") || inputFormatted.equals("P") || inputFormatted.equals("H") 
				|| inputFormatted.matches("R[1-6][1-6]") || inputFormatted.matches("(0[1-9]|1[0-9]|2[0-4]|B[1-2])(0[1-9]|1[0-9]|2[0-4]|T[1-2])");
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
	
	public boolean isRollNumber () {
		return commandType == CommandType.ROLLNUMBER;
	}
	
	public boolean isMove () {
		return commandType == CommandType.MOVE;
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
	
	public boolean isMoveToTerminus () {
		return moveTo.matches("T1|T2");
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
		else // isMoveToTerminus()
			return suitToTerminus(moveTo);
	}
}
