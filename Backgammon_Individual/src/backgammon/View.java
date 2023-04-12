package backgammon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Stack;

/**
 * This class displays to the console and gets user input from the keyboard.
 */

public class View {
	
	private final static String BLANK2 = "  ";
	private final static String BLANK3 = "   ";
	private Scanner in;
	private Command command;
	
	View () {
		in = new Scanner(System.in);
	}
	
	public View (InputStream inputStream) {
	    this.in = new Scanner(inputStream);
	}
	
	public void displayWelcome () {
		System.out.println("Welcome to Backgammon");
	}
	
	public void displayStart () {
		System.out.println("Enter S to start the game or restart the game. Enter Q to exit the game. Enter H to view all allowed commands.");
		System.out.println("Note that some commands are temporarily invalid in some cases.");
	}
	
	public void displayRestart () {
		System.out.println("The game starts over from the beginning!");
	}
	
	public void displayPiece (Board board) {
		String numberStringCurrentPlayerPips = Integer.toString(board.getPlayer(0).getPips());
		String numberStringPlayerREDScore = Integer.toString(board.getPlayer(1).getScore());
		String numberStringPlayerWHITEScore = Integer.toString(board.getPlayer(2).getScore());
		String numberStringMatch = Integer.toString(board.getMatchNumber());
		String numberStringMatchRound = Integer.toString(board.getMatchRoundNumber());
		int numberSpacesCurrentPlayerPips = 4 - numberStringCurrentPlayerPips.length();
		int numberSpacesPlayerREDScoreFormer = 7 - numberStringPlayerREDScore.length() / 2;
		int numberSpacesPlayerREDScoreLater = 8 - (numberStringPlayerREDScore.length() + 1) / 2;
		int numberSpacesPlayerWHITEScoreFormer = 7 - numberStringPlayerWHITEScore.length() / 2;
		int numberSpacesPlayerWHITEScoreLater = 8 - (numberStringPlayerWHITEScore.length() + 1) / 2;
		int numberSpacesMatch = 5 - numberStringMatch.length();
		int numberSpacesMatchRound = 5 - numberStringMatchRound.length();
		int numberUpLane = Math.max(board.getSize("upLane"),1);
		int numberDownLane = Math.max(board.getSize("downLane"),1);
		System.out.println("|---------------------------------------------------------------------|---------------|");
		if (board.getPlayer(0).getColourName() == "RED") {
			System.out.print("| Current player's color: " + DisplayColour.BOLD_RED + board.getPlayer(0).getColourName() + DisplayColour.RESET + "                               pips: " + board.getPlayer(0).getPips());
		} else if (board.getPlayer(0).getColourName() == "WHITE")
			System.out.print("| Current player's color: " + DisplayColour.BOLD_WHITE + board.getPlayer(0).getColourName() + DisplayColour.RESET + "                             pips: " + board.getPlayer(0).getPips());
		for (int i = 0; i < numberSpacesCurrentPlayerPips; i++)
            System.out.print(" ");
		System.out.print("|   Match: " + board.getMatchNumber());
		for (int i = 0; i < numberSpacesMatch; i++)
            System.out.print(" ");
		System.out.println("|");
		System.out.println("|---------------------------------------------------------------------|---------------|");
		if (board.getDiceFace(1) != board.getDiceFace(2)) {
			System.out.print("| Dice:                     ");
			if(board.getDiceMoveStep(1) == 1) {
				System.out.print(board.getDiceFace(1));
			} else
				System.out.print(" ");
			System.out.print("              ");
			if(board.getDiceMoveStep(2) == 1) {
				System.out.print(board.getDiceFace(2));
			} else
				System.out.print(" ");
			System.out.print("                          |");
		} else if (board.getDiceFace(1) == board.getDiceFace(2)) {
			System.out.print("| Dice:      ");
			if(board.getDiceMoveStep(1) >= 1) {
				System.out.print(board.getDiceFace(1));
			} else
				System.out.print(" ");
			System.out.print("              ");
			if(board.getDiceMoveStep(1) >= 2) {
				System.out.print(board.getDiceFace(1));
			} else
				System.out.print(" ");
			System.out.print("              ");
			if(board.getDiceMoveStep(1) >= 3) {
				System.out.print(board.getDiceFace(1));
			} else
				System.out.print(" ");
			System.out.print("              ");
			if(board.getDiceMoveStep(1) == 4) {
				System.out.print(board.getDiceFace(1));
			} else
				System.out.print(" ");
			System.out.print("           |");
		}
		System.out.print("   Round: " + board.getMatchRoundNumber());
		for (int i = 0; i < numberSpacesMatchRound; i++)
            System.out.print(" ");
		System.out.println("|");
		System.out.println("|---------------------------------------------------------------------|---------------|");
		System.out.println("| " + DisplayColour.WHITE + "13   14   15   16   17   18" + DisplayColour.RESET + " | " + DisplayColour.WHITE + "B2" + DisplayColour.RESET + " | " + DisplayColour.WHITE + "19   20   21   22   23   24" + DisplayColour.RESET + " | " + DisplayColour.RED + "E1" + DisplayColour.RESET + " |   " + DisplayColour.BOLD_RED + board.getPlayer(1).getColourName() + DisplayColour.RESET + " Score   |");
		System.out.print("| " + DisplayColour.RED + "12   11   10   09   08   07" + DisplayColour.RESET);
		System.out.print(" | " + DisplayColour.WHITE + "B2"  + DisplayColour.RESET + " | ");
		System.out.print(DisplayColour.RED + "06   05   04   03   02   01" + DisplayColour.RESET + " | " + DisplayColour.RED + "E1" + DisplayColour.RESET + " |");
		for (int i = 0; i < numberSpacesPlayerREDScoreFormer; i++)
            System.out.print(" ");
		System.out.print(board.getPlayer(1).getScore());
		for (int i = 0; i < numberSpacesPlayerREDScoreLater; i++)
            System.out.print(" ");
		System.out.println("|");
		
		for (int row = 0; row < numberUpLane; row++) {
			System.out.print("| ");
			for (int up = 11; up > 6; up--) {
				Stack<Piece> lane = board.getLane(up);
				if (row < lane.size()) {
					System.out.print(lane.get(row) + BLANK3);
		        } else
		            System.out.print(BLANK2 + BLANK3);
			}
			Stack<Piece> lane6 = board.getLane(6);
			if (row < lane6.size()) {
				System.out.print(lane6.get(row) + " | ");
	        } else
	            System.out.print(BLANK2 + " | ");
			Stack<Piece> bar = board.getBar(1);
				if (row < bar.size()) {
					System.out.print(bar.get(row) + " | ");
		        } else
		        	System.out.print(BLANK2 + " | ");
			for (int up = 5; up > 0; up--) {
				Stack<Piece> lane = board.getLane(up);
				if (row < lane.size()) {
					System.out.print(lane.get(row) + BLANK3);
		        } else
		            System.out.print(BLANK2 + BLANK3);
			}
			Stack<Piece> lane0 = board.getLane(0);
			if (row < lane0.size()) {
				System.out.print(lane0.get(row));
	        } else
	            System.out.print(BLANK2);
			if (row==0) {
				if (board.getEndpoint(0).size() < 10)
			        System.out.print(" | " + DisplayColour.BOLD_RED + "0" + board.getEndpoint(0).size() + DisplayColour.RESET + " |---------------|");
			    else
			    	System.out.print(" | " + DisplayColour.BOLD_RED + board.getEndpoint(0).size() + DisplayColour.RESET + " |---------------|");
			} else
				System.out.print(" |    |");
			System.out.println();
		}
		System.out.println("|-----------------------------|----|-----------------------------|----|");
		for (int row = 0; row < numberDownLane; row++) {
			System.out.print("| ");
			for (int up = 12; up < 17; up++) {
				Stack<Piece> lane = board.getLane(up);
				if (row < numberDownLane - lane.size()) {
					System.out.print(BLANK2 + BLANK3);
		        } else
		            System.out.print(lane.get(numberDownLane - row - 1) + BLANK3);
			}
			Stack<Piece> lane17 = board.getLane(17);
			if (row < numberDownLane - lane17.size()) {
				System.out.print(BLANK2 + " | ");
	        } else
	            System.out.print(lane17.get(numberDownLane - row - 1) + " | ");
			Stack<Piece> bar = board.getBar(0);
				if (row < numberDownLane - bar.size()) {
					System.out.print(BLANK2 + " | ");
		        } else
		        	System.out.print(bar.get(numberDownLane - row - 1) + " | ");
			for (int up = 18; up < 23; up++) {
				Stack<Piece> lane = board.getLane(up);
				if (row < numberDownLane - lane.size()) {
					System.out.print(BLANK2 + BLANK3);
		        } else
		        	System.out.print(lane.get(numberDownLane - row - 1) + BLANK3);
			}
			Stack<Piece> lane23 = board.getLane(23);
			if (row < numberDownLane - lane23.size()) {
				System.out.print(BLANK2);
	        } else
	            System.out.print(lane23.get(numberDownLane - row - 1));
			if (row == numberDownLane - 1) {
				if (board.getEndpoint(1).size() < 10)
			        System.out.print(" | " + DisplayColour.BOLD_WHITE + "0" + board.getEndpoint(1).size() + DisplayColour.RESET + " |---------------|");
			    else
			    	System.out.print(" | " + DisplayColour.BOLD_WHITE + board.getEndpoint(1).size() + DisplayColour.RESET + " |---------------|");
			} else
				System.out.print(" |    |");
			System.out.println();
		}
		
		System.out.print("| " + DisplayColour.WHITE + "12   11   10   09   08   07" + DisplayColour.RESET);
		System.out.print(" | " + DisplayColour.RED + "B1" + DisplayColour.RESET + " | ");
		System.out.println(DisplayColour.WHITE + "06   05   04   03   02   01" + DisplayColour.RESET + " | " + DisplayColour.WHITE + "E2" + DisplayColour.RESET + " |  " + DisplayColour.BOLD_WHITE + board.getPlayer(2).getColourName() + DisplayColour.RESET + " Score  |");
		System.out.print("| " + DisplayColour.RED + "13   14   15   16   17   18" + DisplayColour.RESET + " | " + DisplayColour.RED + "B1" + DisplayColour.RESET + " | " + DisplayColour.RED + "19   20   21   22   23   24" + DisplayColour.RESET + " | " + DisplayColour.WHITE + "E2" + DisplayColour.RESET + " |");
		for (int i = 0; i < numberSpacesPlayerWHITEScoreFormer; i++)
            System.out.print(" ");
		System.out.print(board.getPlayer(2).getScore());
		for (int i = 0; i < numberSpacesPlayerWHITEScoreLater; i++)
            System.out.print(" ");
		System.out.println("|");
		System.out.println("|---------------------------------------------------------------------|---------------|");
	}
	
	public Command getUserInput (Board board) {
		boolean commandEntered = false;
		do {
			System.out.print("Enter command: ");
			String input = in.nextLine();
			if (Command.isText(input))
				input = readContentFromFile(input, in, "Please enter a new command: ");
			if (Command.isValid(input)) {
				command = new Command(input);
				if (command.isMove()) {
					String inputFormatted = input.trim();
					if (inputFormatted.length() == 4 && board.getPlayer(0) == board.getPlayer(2)) {
						String[] inputs = new String[2];
						inputs[0] = inputFormatted.substring(0, 2);
						inputs[1] = inputFormatted.substring(2, 4);
						if (inputs[0].matches("\\d+")) {
							int number1 = Integer.parseInt(inputs[0]);
							inputs[0] = String.format("%02d", 25 - number1);
						}
						if (inputs[1].matches("\\d+")) {
							int number2 = Integer.parseInt(inputs[1]);
							inputs[1] = String.format("%02d", 25 - number2);
						}
						inputFormatted = inputs[0] + inputs[1];
					}
					command = new Command(inputFormatted);
				}
				commandEntered = true;
			} else
				System.out.println("The command is invalid. Try again.");
		} while (!commandEntered);
		return command;
	}
	
	public void showAllAllowedMoves(Board board) {
		String inputCleaned, inputRaw;
		int moveStep1, moveStep2;
		int outputCount = 0;
	 	String[] prefixes = new String[25];
	    String[] suffixes = new String[25];
	    if (board.getPlayer(0) == board.getPlayer(1)) {
	    	prefixes[0] = "B1";
			suffixes[24] = "E1";
		    for (int i = 1; i <= 24; i++) {
		        prefixes[25 - i] = String.format("%02d", i);
		        suffixes[24 - i] = String.format("%02d", i);
		    }
	    } else if (board.getPlayer(0) == board.getPlayer(2)) {
	    	prefixes[0] = "B2";
		    suffixes[24] = "E2";
		    for (int i = 1; i <= 24; i++) {
		    	prefixes[25 - i] = String.format("%02d", i);
		        suffixes[24 - i] = String.format("%02d", i);
		    }
	    }
	    for (int i = 0; i < Command.getAllowedMoves().length; i++)
	        Command.setAllowedMoves(i, null);
	    for (int i = 0; i < prefixes.length; i++)
	        for (int j = i; j < suffixes.length; j++) {
	        	inputCleaned = prefixes[i] + suffixes[j];
	        	inputRaw = inputCleaned;
                if (board.getPlayer(0) == board.getPlayer(2)) {
                	String tempPrefix = prefixes[i];
                    String tempSuffix = suffixes[j];
					if (prefixes[i].matches("\\d+")) {
						int number1 = Integer.parseInt(prefixes[i]);
						tempPrefix = String.format("%02d", 25 - number1);
					}
					if (suffixes[j].matches("\\d+")) {
						int number2 = Integer.parseInt(suffixes[j]);
						tempSuffix = String.format("%02d", 25 - number2);
					}
					inputCleaned = tempPrefix + tempSuffix;
                }
				command = new Command(inputCleaned);
                moveStep1 = board.getDiceMoveStep(1);
                moveStep2 = board.getDiceMoveStep(2);
                if (board.moveIsPossible(command)) {
                	Command.setAllowedMoves(outputCount, inputCleaned);
                	outputCount++;
                	if (outputCount == 1)
                    	System.out.println("Here are the moves you can make:");
                	if (outputCount > 0 && outputCount < 10)
                		System.out.print(" ");
                    System.out.println(outputCount + ": " + inputRaw);
                }
                board.setDiceMoveStep(moveStep1, moveStep2);
	        }
	    if (outputCount == 0)
        	System.out.println("You have no moves available at this time, and must forfeit your turn if you have already rolled the dice.");
	}
	
	public void FirstDiceRoll (Board board) {
		do {
			board.makeDiceRoll();
			if (board.getDiceFace(1) > board.getDiceFace(2)) {
				System.out.println("Die 1 is " + board.getDiceFace(1) + ". Die 2 is " + board.getDiceFace(2)+ ". The number in Die 1 is bigger than the number in Die 2. So Red goes first.");
				board.setCurrentPlayer(1);
			} else if (board.getDiceFace(1) < board.getDiceFace(2)) {
				System.out.println("Die 1 is " + board.getDiceFace(1) + ". Die 2 is " + board.getDiceFace(2)+ ". The number in Die 2 is bigger than the number in Die 1. So White goes first.");
				board.setCurrentPlayer(2);
			} else if (board.getDiceFace(1) == board.getDiceFace(2)) {
				System.out.println("Die 1 is " + board.getDiceFace(1) + ". Die 2 is " + board.getDiceFace(2)+ ". The number in Die 1 is equal to the number in Die 2. So Reroll the dies.");
			}
		} while (board.getDiceFace(1) == board.getDiceFace(2));
	}
	
	public void getStartInformation (Board board) {
		boolean validInput = false;
		String promptMessage = "Please enter the length of the match:";
		System.out.print("Enter name of player RED: ");
		board.initializePlayer(1);
		System.out.println("The name of player RED is " + board.getPlayer(1) + ".");
		System.out.print("Enter name of player WHITE: ");
		board.initializePlayer(2);
		System.out.println("The name of player WHITE is " + board.getPlayer(2) + ".");
		while (!validInput) {
            System.out.print(promptMessage);
            String matchNumberInput = in.nextLine();
    		if (Command.isText(matchNumberInput))
    			matchNumberInput = readContentFromFile(matchNumberInput, in, "Please enter a new length of the match: ");
            try {
                double doubleValue = Double.parseDouble(matchNumberInput);
                if (doubleValue > 0 && Math.floor(doubleValue) == doubleValue) {
                    board.setMatchNumber((int) doubleValue);
                    board.setMatchRoundNumber(1);
                    validInput = true;
                } else if (Math.floor(doubleValue) != doubleValue) {
                    System.out.println("Error: The entered number is a decimal, please try again.");
                } else {
                    System.out.println("Error: The entered number is not a positive integer, please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: The entered string cannot be converted to a number, please try again.");
            }
            promptMessage = "Please enter a new length of the match:";
        }
        System.out.println("The length of the match is: " + board.getMatchNumber());
	}
	
	public void displayCommandNotPossible () {
		System.out.println("That play is not possible. Try again.");
	}
	
	public void displayCommandTemporarilyInvalid () {
		System.out.println("The command is temporarily invalid. Try again.");
	}
	
	public void displayOneMatchOver (Board board) {
		System.out.println("Round " + board.getMatchRoundNumber() + " of the competition is over. " + board.getPlayer(0).getNamewithColor() + " wins the current match round.");
		if (board.getMatchNumber() == 1) {
			System.out.println("There is " + board.getMatchNumber() + " round in total, so now the whole match is over.");
		} else if (board.getMatchNumber() > 1)
			System.out.print("There are " + board.getMatchNumber() + " rounds in total, ");
		if (board.getMatchNumber() - board.getMatchRoundNumber() == 1) {
			System.out.println("so there is still " + (board.getMatchNumber() - board.getMatchRoundNumber()) + " round left to play. Please press J to play the next round.");
		} else if (board.getMatchNumber() - board.getMatchRoundNumber() > 1)
			System.out.println("so there are still " + (board.getMatchNumber() - board.getMatchRoundNumber()) + " rounds left to play. Please press J to play the next round.");
	}
	
	public void displayForceJump (Board board) {
		System.out.println("The current match round was forced to end early. No player accumulates points because of this.");
		if (board.getMatchNumber() == 1) {
			System.out.println("There is " + board.getMatchNumber() + " round in total, so now the whole match is over.");
		} else if (board.getMatchNumber() > 1)
			System.out.print("There are " + board.getMatchNumber() + " rounds in total, ");
		if (board.getMatchNumber() - board.getMatchRoundNumber() == 1) {
			System.out.println("so there is still " + (board.getMatchNumber() - board.getMatchRoundNumber()) + " round left to play. The next match round has already begun.");
		} else if (board.getMatchNumber() - board.getMatchRoundNumber() > 1)
			System.out.println("so there are still " + (board.getMatchNumber() - board.getMatchRoundNumber()) + " rounds left to play. The next match round has already begun.");
	}
	
	public void displayWholeMatchOver (Board board) {
		if (board.getPlayer(1).getScore() > board.getPlayer(2).getScore()) {
			System.out.println(board.getPlayer(1).getNamewithColor() + " wins the whole match.");
		} else if (board.getPlayer(1).getScore() < board.getPlayer(2).getScore()) {
			System.out.println(board.getPlayer(2).getNamewithColor() + " wins the whole match.");
		} else if (board.getPlayer(1).getScore() == board.getPlayer(2).getScore())
			System.out.println("the two players play to a draw.");
		System.out.println("Game over.");
	}
	
	public void displayQuit () {
		System.out.println("Quit.");
	}
	
	public void playerTurnCurrent (Player player) {
		System.out.println(player + "(" + player.getColourName() + ") finishes moving.");
	}
	
	public void playerTurnNext (Player player) {
		System.out.println("Now it's the " + player + "(" + player.getColourName() + ")'s turn to play.");
	}
	
	public void showDice (int face1, int face2) {
		if (face1 != face2) {
			System.out.println("The number of 2 dice thrown are " + face1 + " and " + face2 + ". So please move 2 times.");
		} else if (face1 == face2)
			System.out.println("The number of 2 dice thrown are " + face1 + " and " + face2 + ", which means that the number of moveable dice is doubled because 2 dice rolled show the same number. So please move 4 times.");
	}
	
	public void displayWaive (Player player) {
		System.out.println(player + " forfeits the turn as he has no pieces to play legally or he doesn't know how to move the pieces further. Now skip to the other player's turn.");
	}
	
	public void displayPips (Board board) {
		System.out.println(board.getPlayer(1).getNamewithColor() + "'s current pips are " + board.getPlayer(1).getPips() + ".");
		System.out.println(board.getPlayer(2).getNamewithColor() + "'s current pips are " + board.getPlayer(2).getPips() + ".");
	}
	
	public String readContentFromFile(String string, Scanner in, String promptMessage) {
	    boolean fileReadSuccess = false;
	    String fileContent = "";
	    do {
	        String fileName = Command.getText(string);
	        File dir = new File(".");
	        File[] exactMatches = dir.listFiles((dir1, name) -> name.equals(fileName));
	        File[] caseInsensitiveMatches = dir.listFiles((dir1, name) -> name.equalsIgnoreCase(fileName));
	        if (!((caseInsensitiveMatches != null && caseInsensitiveMatches.length > 0) && !(exactMatches != null && exactMatches.length > 0))) {
	            try {
	                BufferedReader br = new BufferedReader(new FileReader(fileName));
	                StringBuilder fileContentBuilder = new StringBuilder();
	                String line;
	                while ((line = br.readLine()) != null)
	                    fileContentBuilder.append(line).append("\n");
	                br.close();
	                fileContent = fileContentBuilder.toString().trim(); // Store the file content
	                fileReadSuccess = true;
	            } catch (FileNotFoundException e) {
	                System.out.println("Error: File not found - " + e.getMessage());
	            } catch (IOException e) {
	                System.out.println("Error reading file: " + e.getMessage());
	            }
	        } else {
	            System.out.println("A file exists with only the case of the file name different from the input file name.");
	        }
	        if (!fileReadSuccess) {
	            System.out.print(promptMessage);
	            if (in.hasNextLine()) { // Add a condition to check if the Scanner has the next line
	                string = in.nextLine();
	            } else
	                break; // If there's no next line, break the loop
	        }
	    } while (!fileReadSuccess);
	    return fileContent;
	}
	
	public Scanner getScanner() { // Only for Test.
	    return in;
	}
	
	public void showHint () {
		System.out.println("S: Start Backgammon or restart Backgammon.");
		System.out.println("R: Roll the dice.");
		System.out.println("R + 1 digit + 1 digit: Roll the dice to get 2 dice numbers as specified.");
		System.out.println("W: Waive current turn.");
		System.out.println("P: View 2 players' pips.");
		System.out.println("2 digits + 2 digits: Move a piece on Lane.");
		System.out.println("B + 1 digit + 2 digits: Move a piece from Bar to Lane.");
		System.out.println("2 digits + E + 1 digit: Move a piece from Lane to Endpoint.");
		System.out.println("H: View all allowed commands.");
		System.out.println("M: View all allowed moves.");
		System.out.println("J: Regardless of whether or not the current match round is completed, the current match round will end and the next match round will be played.");
		System.out.println("Q: Quit the game.");
		System.out.println("If you type \"test:file_name.txt\", the game will read the commands in that file.");
	}
}
